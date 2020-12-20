package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditProjectBinding

class EditProjectFragment :
    BaseFragment<FragmentEditProjectBinding>(R.layout.fragment_edit_project) {


    companion object {
        fun newInstance() =
            EditProjectFragment()
    }
}