package com.iron.espresso

import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentLoadingBinding

class LoadingFragment : BaseFragment<FragmentLoadingBinding>(R.layout.fragment_loading) {


    companion object {
        fun newInstance() =
            LoadingFragment()
    }
}