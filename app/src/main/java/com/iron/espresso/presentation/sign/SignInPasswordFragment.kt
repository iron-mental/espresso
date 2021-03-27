package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignInPasswordBinding
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignInPasswordFragment :
    BaseFragment<FragmentSignInPasswordBinding>(R.layout.fragment_sign_in_password) {

    private val signInViewModel by activityViewModels<SignInViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = signInViewModel
            inputPwd.requestFocus()
        }
        signInViewModel.run {
            checkType.observe(viewLifecycleOwner, { type ->
                when (type) {
                    CheckType.CHECK_PASSWORD_FAIL -> {

                    }
                }
            })
        }

        compositeDisposable += binding.inputPwd.textChanges()
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribe({ text ->
                if (text.length < 8) {
                    binding.passwordField.error = getString(R.string.sign_in_password_helper)
                } else {
                    binding.passwordField.error = null
                }
            }, {

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_action, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> {
                signInViewModel.run {
                    verifyPasswordCheck(signInViewModel.signInPassword.value)
                }
            }
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}