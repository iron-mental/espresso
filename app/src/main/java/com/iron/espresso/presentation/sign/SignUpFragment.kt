package com.iron.espresso.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignUpBinding
import com.iron.espresso.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val signUpViewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = signUpViewModel
            signUpViewModel.startViewModel()
            startFragment(SignUpEmailFragment())
            barSignUp.bringToFront()
        }

        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignUpNicknameFragment())
                    }
                    CheckType.CHECK_NICKNAME_SUCCESS -> {
                        startFragment(SignUpPasswordFragment())
                    }
                    CheckType.CHECK_PASSWORD_SUCCESS -> {
                        registerUser()
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


    private fun startFragment(fragment: Fragment) {
        binding.containerSignUp.bringToFront()
        parentFragmentManager.beginTransaction()
            .replace(R.id.container_sign_up, fragment)
            .commit()
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