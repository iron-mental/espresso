package com.iron.espresso.presentation.home.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val settingViewModel by viewModels<SettingViewModel>()
    private val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        when(activityResult.resultCode) {
            RESULT_OK -> {
                toast("resarea")
                settingViewModel.refreshProfile()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProfile()
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.run {
            viewModel = settingViewModel

            profile.root.setOnClickListener {
                requestActivity.launch(ProfileActivity.getInstance(requireContext()))
            }
            contact.root.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(TO_EMAIL))
                startActivity(email)
            }
            terms.root.setOnClickListener {
                startWeb(TERMS_URL)
            }
            policy.root.setOnClickListener {
                startWeb(PRIVACY_URL)
            }
        }
    }

    private fun setupViewModel() {
        settingViewModel.run {
            refreshed.observe(viewLifecycleOwner, EventObserver {
                setProfile()
            })
        }
    }

    private fun setProfile() {
        UserHolder.get()?.let {
            settingViewModel.setProfile(user = it)

            binding.profile.settingProfileImage.setCircleImage(it.image)
        }
    }

    private fun startWeb(url: String) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(requireContext(), Uri.parse(url))
        }
    }

    companion object {
        private const val TERMS_URL = "https://www.terminal-study.tk/terms"
        private const val PRIVACY_URL = "https://www.terminal-study.tk/privacy"
        private const val TO_EMAIL = "team.ironmental@gmail.com"

        fun newInstance() =
            SettingFragment()
    }
}