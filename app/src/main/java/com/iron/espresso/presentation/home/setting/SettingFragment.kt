package com.iron.espresso.presentation.home.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.presentation.profile.ProfileActivity

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

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