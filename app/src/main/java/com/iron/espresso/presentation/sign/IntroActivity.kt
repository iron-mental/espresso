package com.iron.espresso.presentation.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            signInButton.setOnClickListener {
                startActivity(SignInActivity.getIntent(this@IntroActivity))
            }

            signUpButton.setOnClickListener {
                startActivity(SignUpActivity.getIntent(this@IntroActivity))
            }
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, IntroActivity::class.java)
    }

}