package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditAreaBinding

class EditAreaFragment :
    BaseFragment<FragmentEditAreaBinding>(R.layout.fragment_edit_area) {


    companion object {
        fun newInstance() =
            EditAreaFragment()
    }
}