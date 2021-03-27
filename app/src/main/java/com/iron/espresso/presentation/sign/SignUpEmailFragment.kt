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
import com.iron.espresso.databinding.FragmentSignUpEmailBinding
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignUpViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

@AndroidEntryPoint
class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {

    private val signUpViewModel by activityViewModels<SignUpViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = signUpViewModel
        }

        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner) { type ->
                if (type == CheckType.CHECK_EMAIL_FAIL) {
                    Logger.d("SignUpViewModel type.message ${type.message}")
                    if (type.message.isNotEmpty()) {
                        binding.emailField.error = type.message
                    } else {
                        binding.emailField.error = getString(R.string.invalid_email)
                    }
                }
            }
        }

        compositeDisposable += binding.inputEmail.textChanges()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ email ->
                binding.emailField.error = null
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
                signUpViewModel.run {
                    verifyEmailCheck(signUpViewModel.signUpEmail.value)
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
            SignUpEmailFragment()
    }
}

