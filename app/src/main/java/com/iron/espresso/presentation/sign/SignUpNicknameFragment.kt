package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignUpNicknameBinding
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpNicknameFragment :
    BaseFragment<FragmentSignUpNicknameBinding>(R.layout.fragment_sign_up_nickname) {

    private val signUpViewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            vm = signUpViewModel
        }
        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner) { type ->
                when (type) {
                    CheckType.CHECK_NICKNAME_FAIL -> {

                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_action, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> {
                signUpViewModel.run {
                    verifyNicknameCheck(signUpViewModel.signUpNickname.value)
                }
            }
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() =
            SignUpNicknameFragment()
    }
}