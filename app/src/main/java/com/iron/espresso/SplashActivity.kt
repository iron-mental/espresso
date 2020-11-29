package com.iron.espresso

import android.os.Bundle
import androidx.activity.viewModels
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySplashBinding
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.presentation.sign.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userAuth = AuthHolder.get(this)
        if (userAuth != null) {
            val accessToken = userAuth.accessToken
            val userId = userAuth.id
            if (accessToken != null && userId != null) {
                viewModel.checkTokenAndResult("Bearer $accessToken", userId)
            }
        } else {
            startActivity(IntroActivity.getIntent(this))
            finish()
        }

        viewModel.userInfoResult.observe(this, { userResponse ->
            UserHolder.set(this, userResponse)
            startActivity(HomeActivity.getIntent(this))
            finish()
        })
    }
}

