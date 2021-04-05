package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignInEmailBinding
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

@AndroidEntryPoint
class SignInEmailFragment :
    BaseFragment<FragmentSignInEmailBinding>(R.layout.fragment_sign_in_email) {

    private val signInViewModel by activityViewModels<SignInViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = signInViewModel
        }

        signInViewModel.run {
            checkType.observe(viewLifecycleOwner, { type ->
                if (type == CheckType.CHECK_EMAIL_FAIL) {
                    binding.emailField.error = type.message
                }
            })
        }

        binding.inputEmail.requestFocus()

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
                signInViewModel.run {
                    verifyEmailCheck(signInViewModel.signInEmail.value)
                }
            }
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

