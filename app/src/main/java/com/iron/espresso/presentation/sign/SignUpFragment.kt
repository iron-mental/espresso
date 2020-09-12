package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val signUpViewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = signUpViewModel
            signUpViewModel.startViewModel()
        }

        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL -> {
                        startFragment(SignUpEmailFragment())
                    }
                    CheckType.CHECK_PASSWORD -> {
                        startFragment(SignUpPasswordFragment())
                    }
                    CheckType.CHECK_NICKNAME -> {
                        startFragment(SignUpNicknameFragment())
                    }
                    CheckType.CHECK_ALL -> {
                    }
                }
            })
        }
    }

    private fun startFragment(fragment: Fragment) {
        binding.fragmentContainerSignUp.bringToFront()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_sign_up, fragment)
            .commit()
    }

}