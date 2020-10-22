package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSignUpPasswordBinding
import com.iron.espresso.utils.ToolbarHelper
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpPasswordFragment :
    BaseFragment<FragmentSignUpPasswordBinding>(R.layout.fragment_sign_up_password) {

    private val signUpViewModel by sharedViewModel<SignUpViewModel>()

    private lateinit var toolbarHelper: ToolbarHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarHelper = ToolbarHelper(activity as AppCompatActivity, binding.appBar).apply {
            setNavigationIcon(R.drawable.ic_clear)
        }

        binding.apply {
            vm = signUpViewModel
        }
        signUpViewModel.run {
            checkType.observe(viewLifecycleOwner, Observer { type ->
                when (type) {
                    CheckType.CHECK_PASSWORD_FAIL -> {

                    }
                }
            })
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
                    verifyPasswordCheck(signUpViewModel.signUpPassword.value)
                }
            }
            android.R.id.home -> {
                signUpViewModel.exitViewModel()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}