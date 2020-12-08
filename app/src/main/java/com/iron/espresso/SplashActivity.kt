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

        val bearerToken = AuthHolder.bearerToken
        val userId = AuthHolder.id
        if (bearerToken.isNotEmpty() && userId != null) {
            viewModel.checkTokenAndResult(bearerToken, userId)
        } else {
            startActivity(IntroActivity.getIntent(this))
            finish()
        }

        viewModel.userInfoResult.observe(this, { userResponse ->
            UserHolder.set(userResponse)
            startActivity(HomeActivity.getIntent(this))
            finish()
        })
    }
}

