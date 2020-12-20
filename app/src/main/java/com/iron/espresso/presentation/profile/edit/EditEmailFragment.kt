package com.iron.espresso.presentation.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentEditEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEmailFragment :
    BaseFragment<FragmentEditEmailBinding>(R.layout.fragment_edit_email) {

    private val viewModel by viewModels<EditEmailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() =
            EditEmailFragment()
    }
}