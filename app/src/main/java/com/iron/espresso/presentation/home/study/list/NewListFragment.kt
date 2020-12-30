package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNewListBinding

class NewListFragment : BaseFragment<FragmentNewListBinding>(R.layout.fragment_new_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() =
            NewListFragment()
    }
}

