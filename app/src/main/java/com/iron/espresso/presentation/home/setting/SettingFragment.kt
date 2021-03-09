package com.iron.espresso.presentation.home.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.browser.customtabs.CustomTabsIntent
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.presentation.profile.ProfileActivity

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testButton = Button(context).apply {
            text = "프로필 상세"
            setOnClickListener {
                startActivity(ProfileActivity.getInstance(context))
            }
        }

        (view as ViewGroup).addView(testButton)

        binding.run {
            contact.root.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf("team.ironmental@gmail.com"))
                startActivity(email)
            }
            terms.root.setOnClickListener {
                startWeb("https://www.terminal-study.tk/terms")
            }
            policy.root.setOnClickListener {
                startWeb("https://www.terminal-study.tk/privacy")
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
        fun newInstance() =
            SettingFragment()
    }
}