package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditCareerBinding

class EditCareerFragment :
    BaseFragment<FragmentEditCareerBinding>(R.layout.fragment_edit_career) {


    companion object {
        fun newInstance() =
            EditCareerFragment()
    }
}
