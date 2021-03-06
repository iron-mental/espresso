package com.iron.espresso.presentation.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.iron.espresso.*
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignInBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.ext.toast
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
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Logger.d("Fetching FCM registration token failed ${task.exception}")
                                    return@OnCompleteListener
                                }

                                // Get new FCM registration token
                                val token = task.result

                                if (token != null) {
                                    viewModel.checkLogin(
                                        viewModel.signInEmail.value.orEmpty(),
                                        viewModel.signInPassword.value.orEmpty(),
                                        pushToken = token
                                    )
                                }
                            })
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        toast(R.string.login_success)
                    }
                    CheckType.CHECK_ALL_FAIL -> {
                        toast(R.string.login_failed)
                    }
                }
            }

            toastMessage.observe(this@SignInActivity, EventObserver(::toast))
            loadingState.observe(this@SignInActivity, EventObserver(::setLoading))

            userAuth.observe(this@SignInActivity) { authResponse ->
                if (AuthHolder.set(authResponse)) {
                    val userId = authResponse.id ?: -1

                    if (userId != -1) {
                        viewModel.getUserInfo(userId)
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