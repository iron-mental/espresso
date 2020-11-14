package com.iron.espresso.presentation.sign

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignInBinding
import com.iron.espresso.ext.startActivity
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val signInViewModel by viewModels<SignInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = signInViewModel
            startFragment(SignInEmailFragment())
        }

        signInViewModel.run {
            checkType.observe(this@SignInActivity, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignInPasswordFragment())
                    }
                    CheckType.CHECK_PASSWORD_SUCCESS -> {
//                        registerUser()
                        startActivity<HomeActivity>()
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        startActivity<HomeActivity>()
                    }
                    CheckType.CHECK_ALL_FAIL -> {

                    }
                }
            })

            exitIdentifier.observe(this@SignInActivity) { isExit ->
                if (isExit) exitFragment()
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        binding.containerSignIn.bringToFront()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_sign_in, fragment)
            .commit()
    }

    private fun exitFragment() {
        startActivity<IntroActivity>()
    }
}