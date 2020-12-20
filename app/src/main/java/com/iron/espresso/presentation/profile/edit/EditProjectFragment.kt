package com.iron.espresso.presentation.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditProjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProjectFragment :
    BaseFragment<FragmentEditProjectBinding>(R.layout.fragment_edit_project) {

    private val viewModel by viewModels<EditProjectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
    }
    companion object {
        fun newInstance() =
            EditProjectFragment()
    }
}