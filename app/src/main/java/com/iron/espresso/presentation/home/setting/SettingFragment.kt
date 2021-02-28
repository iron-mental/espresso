package com.iron.espresso.presentation.home.setting

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    }

    companion object {
        fun newInstance() =
            SettingFragment()
    }
}