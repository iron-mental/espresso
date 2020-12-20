package com.iron.espresso.presentation.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditAreaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAreaFragment :
    BaseFragment<FragmentEditAreaBinding>(R.layout.fragment_edit_area) {

    private val viewModel by viewModels<EditAreaViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() =
            EditAreaFragment()
    }
}