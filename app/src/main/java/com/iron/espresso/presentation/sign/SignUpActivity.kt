package com.iron.espresso.presentation.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignUpBinding
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_clear)

        binding.apply {
            vm = signUpViewModel
            startFragment(SignUpEmailFragment.newInstance())
        }

        signUpViewModel.run {
            checkType.observe(this@SignUpActivity) { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignUpNicknameFragment.newInstance())
                    }
                    CheckType.CHECK_NICKNAME_SUCCESS -> {
                        startFragment(SignUpPasswordFragment.newInstance())
                    }
                    CheckType.CHECK_PASSWORD_SUCCESS -> {

                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        startActivity(HomeActivity.getIntent(this@SignUpActivity))
                    }
                    CheckType.CHECK_ALL_FAIL -> {

                    }
                }
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        val beforeFragment = supportFragmentManager.findFragmentById(R.id.container_sign_up)
        val transaction = supportFragmentManager.beginTransaction()
        if (beforeFragment != null) {
            transaction.hide(beforeFragment)
        }
        transaction
            .add(R.id.container_sign_up, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container_sign_up)
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()

            val beforeFragment = supportFragmentManager.findFragmentById(R.id.container_sign_up)
            if (beforeFragment != null) {
                supportFragmentManager.beginTransaction()
                    .show(beforeFragment)
                    .commit()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, SignUpActivity::class.java)
    }
}

