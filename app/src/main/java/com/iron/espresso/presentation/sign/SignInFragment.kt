package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.View
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signInViewModel by sharedViewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = signInViewModel
        }
    }
}