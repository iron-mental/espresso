package com.iron.espresso.presentation.home.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.iron.espresso.AuthHolder
import com.iron.espresso.BuildConfig
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.toast
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import com.iron.espresso.presentation.profile.ProfileActivity
import com.iron.espresso.presentation.sign.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val settingViewModel by viewModels<SettingViewModel>()
    private val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        when (activityResult.resultCode) {
            RESULT_OK -> {
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

    override fun onResume() {
        settingViewModel.refreshProfile()
        super.onResume()
    }

    private fun setupView() {
        binding.run {
            viewModel = settingViewModel

            appVersionNumber.text = BuildConfig.VERSION_NAME

            profile.root.setOnClickListener {
                requestActivity.launch(ProfileActivity.getInstance(requireContext()))
            }

            certifyEmail.setOnClickListener {
                toast("인증된 이메일입니다 (임시)")
            }

            notCertifyEmail.setOnClickListener {
                showVerifyEmailDialog()
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

            logout.setOnClickListener {
                showLogoutDialog()
            }

            membershipWithdrawal.setOnClickListener {
                startActivity(DeleteUserActivity.getIntent(requireContext()))
            }
        }
    }

    private fun setupViewModel() {
        settingViewModel.run {
            toastMessage.observe(viewLifecycleOwner, EventObserver(::toast))

            refreshed.observe(viewLifecycleOwner, EventObserver {
                setProfile()
            })

            successEvent.observe(viewLifecycleOwner, EventObserver {
                AuthHolder.set(UserAuthResponse("", "", -1))

                startActivity(
                    IntroActivity.getIntent(requireContext())
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            })
        }
    }

    private fun showVerifyEmailDialog() {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_certify_email))

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == RESULT_OK) {
                settingViewModel.emailVerify()
            }
        }
    }

    private fun showLogoutDialog() {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_logout_title))

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == RESULT_OK) {
                settingViewModel.logout()
            }
        }
    }

    private fun setProfile() {
        UserHolder.get()?.let {
            settingViewModel.setProfile(user = it)

            binding.run {
                certifyEmail.isVisible = it.emailVerified
                notCertifyEmail.isVisible = !it.emailVerified
                profile.settingProfileImage.setCircleImage(it.image)
            }
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