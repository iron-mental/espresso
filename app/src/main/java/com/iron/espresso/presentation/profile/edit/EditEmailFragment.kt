package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditEmailBinding

class EditEmailFragment :
    BaseFragment<FragmentEditEmailBinding>(R.layout.fragment_edit_email) {


    companion object {
        fun newInstance() =
            EditEmailFragment()
    }
}