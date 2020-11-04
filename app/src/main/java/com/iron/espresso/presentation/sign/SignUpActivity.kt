package com.iron.espresso.presentation.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignUpBinding
import com.iron.espresso.ext.startActivity
import com.iron.espresso.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel by viewModel<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = signUpViewModel
            startFragment(SignUpEmailFragment())
        }

        signUpViewModel.run {
            checkType.observe(this@SignUpActivity) { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignUpNicknameFragment())
                    }
                    CheckType.CHECK_NICKNAME_SUCCESS -> {
                        startFragment(SignUpPasswordFragment())
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
            }
            exitIdentifier.observe(this@SignUpActivity) { isExit ->
                if (isExit) exitFragment()
            }

//            registerUser("duksung3@naver.com","ejrtjd12","조던보단반스")
        }
    }

    private fun startFragment(fragment: Fragment) {
        binding.containerSignUp.bringToFront()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_sign_up, fragment)
            .commit()
    }

    private fun exitFragment() {
        startActivity<IntroActivity>()
    }

}