package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignInPasswordBinding
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
                if (type == CheckType.CHECK_PASSWORD_FAIL && type.message.isNotEmpty()) {
                    binding.passwordField.error = type.message
                }
            })
        }

        compositeDisposable += binding.inputPwd.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                if (text.length in 1..5) {
                    binding.passwordField.error = getString(R.string.sign_in_password_helper)
                } else {
                    binding.passwordField.error = null
                }
            }, {
                Logger.d("$it")
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