package com.iron.espresso.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignInBinding
import com.iron.espresso.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signInViewModel by sharedViewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = signInViewModel
            signInViewModel.startViewModel()
        }

        signInViewModel.run {
            checkType.observe(viewLifecycleOwner, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_FAIL -> {
                    }
                    CheckType.CHECK_PASSWORD_FAIL -> {
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        startMain()
                    }
                    CheckType.CHECK_ALL_FAIL -> {
                    }
                }
            })
            exitIdentifier.observe(viewLifecycleOwner, Observer { isExit ->
                if (isExit) exitFragment()
            })
        }
    }

    private fun exitFragment() {
        parentFragmentManager.popBackStack()
    }

    private fun startMain() {
        startActivity(
            Intent(activity?.application, HomeActivity::class.java)
        ).apply {
            activity?.finish()
        }
    }
}