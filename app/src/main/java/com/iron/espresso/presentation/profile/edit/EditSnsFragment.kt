package com.iron.espresso.presentation.profile.edit

import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditEmailBinding

class EditSnsFragment :
    BaseFragment<FragmentEditEmailBinding>(R.layout.fragment_edit_sns) {


    companion object {
        fun newInstance() =
            EditSnsFragment()
    }
}