package com.iron.espresso.presentation.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.iron.espresso.App
import com.iron.espresso.AuthHolder
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignInBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.presentation.viewmodel.CheckType
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_clear)

        binding.apply {
            vm = viewModel
            startFragment(SignInEmailFragment())
        }

        viewModel.run {
            checkType.observe(this@SignInActivity) { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignInPasswordFragment())
                    }
                    CheckType.CHECK_PASSWORD_SUCCESS -> {
                        viewModel.checkLogin(
                            viewModel.signInEmail.value.orEmpty(),
                            viewModel.signInPassword.value.orEmpty()
                        )
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        Toast.makeText(App.instance.context(), "로그인 성공", Toast.LENGTH_SHORT).show()
                    }
                    CheckType.CHECK_ALL_FAIL -> {
                        Toast.makeText(App.instance.context(), "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            toastMessage.observe(this@SignInActivity, EventObserver {
                Toast.makeText(this@SignInActivity, it, Toast.LENGTH_SHORT).show()
            })

            userAuth.observe(this@SignInActivity) { authResponse ->
                if (AuthHolder.set(authResponse)) {
                    val accessToken  = authResponse.accessToken
                    val userId = authResponse.id

                    if (accessToken != null && userId != null) {
                        viewModel.getUserInfo("Bearer $accessToken", userId)
                    }
                }
            }

            userInfo.observe(this@SignInActivity) { userInfo ->
                if (UserHolder.set(userInfo)) {
                    startActivity(HomeActivity.getIntent(this@SignInActivity).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                }
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        val beforeFragment = supportFragmentManager.findFragmentById(R.id.container_sign_in)
        val transaction = supportFragmentManager.beginTransaction()
        if (beforeFragment != null) {
            transaction.hide(beforeFragment)
        }
        transaction
            .add(R.id.container_sign_in, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container_sign_in)
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()

            val beforeFragment = supportFragmentManager.findFragmentById(R.id.container_sign_in)
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
            Intent(context, SignInActivity::class.java)
    }
}