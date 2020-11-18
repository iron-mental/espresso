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
import com.iron.espresso.base.ToolbarHelper
import com.iron.espresso.databinding.FragmentSignInEmailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInEmailFragment :
    BaseFragment<FragmentSignInEmailBinding>(R.layout.fragment_sign_in_email) {

    private val signInViewModel by sharedViewModel<SignInViewModel>()

    private lateinit var toolbarHelper: ToolbarHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarHelper = ToolbarHelper(activity as AppCompatActivity, binding.appbar).apply {
            setNavigationIcon(R.drawable.ic_clear)
        }

        binding.apply {
            vm = signInViewModel
        }

        signInViewModel.run {
            checkType.observe(viewLifecycleOwner, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_FAIL -> {
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
                signInViewModel.run {
                    verifyEmailCheck(signInViewModel.signInEmail.value)
                }
            }
            android.R.id.home -> {
                signInViewModel.exitViewModel()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

