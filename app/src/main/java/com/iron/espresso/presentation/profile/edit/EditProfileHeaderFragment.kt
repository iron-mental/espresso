package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditProfileHeaderBinding

class EditProfileHeaderFragment :
    BaseFragment<FragmentEditProfileHeaderBinding>(R.layout.fragment_edit_profile_header) {


    companion object {
        fun newInstance() =
            EditProfileHeaderFragment()
    }
}