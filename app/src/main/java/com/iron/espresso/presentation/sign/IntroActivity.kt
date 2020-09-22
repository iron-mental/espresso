package com.iron.espresso.presentation.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityIntroBinding
import com.iron.espresso.ext.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    private val introViewModel by viewModel<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = introViewModel
        }

        introViewModel.run {
            clickTypeIdentifier.observe(this@IntroActivity, Observer { type ->
                when (type) {
                    SignType.TYPE_SIGN_IN -> {
                        startActivity<SignInActivity>()
                    }
                    SignType.TYPE_SIGN_UP -> {
                        startFragment(SignUpFragment())
                    }
                }
            })
        }
    }

    private fun startFragment(fragment: Fragment) {
        binding.fragmentContainerIntroView.bringToFront()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_intro_view, fragment)
            .addToBackStack(null)
            .commit()
    }

}