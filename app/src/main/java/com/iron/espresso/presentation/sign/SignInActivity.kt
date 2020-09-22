package com.iron.espresso.presentation.sign

import android.os.Bundle
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignInBinding
import com.iron.espresso.ext.startActivity
import com.iron.espresso.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val signInViewModel by viewModel<SignInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = signInViewModel
        }

        signInViewModel.run {
            checkType.observe(this@SignInActivity, Observer { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_FAIL -> {
                    }
                    CheckType.CHECK_PASSWORD_FAIL -> {
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        startActivity<HomeActivity>()
                    }
                    CheckType.CHECK_ALL_FAIL -> {
                    }
                }
            })
            exitIdentifier.observe(this@SignInActivity, Observer { isExit ->
                if (isExit) startActivity<IntroActivity>()
            })
        }

    }

}