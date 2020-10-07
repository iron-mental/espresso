package com.iron.espresso.presentation.sign

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySignUpBinding
import com.iron.espresso.ext.startActivity
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.utils.ToolbarHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel by viewModel<SignUpViewModel>()

    private lateinit var toolbarHelper: ToolbarHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        toolbarHelper = ToolbarHelper(this, binding.appBar).apply {
            setNavigationIcon(R.drawable.ic_clear)
        }

        binding.apply {
            vm = signUpViewModel
            startFragment(SignUpEmailFragment())
            barSignUp.bringToFront()
        }

        signUpViewModel.run {
            checkType.observe(this@SignUpActivity, { type ->
                when (type) {
                    CheckType.CHECK_EMAIL_SUCCESS -> {
                        startFragment(SignUpNicknameFragment())
                    }
                    CheckType.CHECK_NICKNAME_SUCCESS -> {
                        startFragment(SignUpPasswordFragment())
                    }
                    CheckType.CHECK_PASSWORD_SUCCESS -> {
                        registerUser()
                    }
                    CheckType.CHECK_ALL_SUCCESS -> {
                        startActivity<HomeActivity>()
                    }
                    CheckType.CHECK_ALL_FAIL -> {

                    }
                }
            })
            exitIdentifier.observe(this@SignUpActivity, { isExit ->
                if (isExit) exitFragment()
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> {
                Toast.makeText(this, "다음", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                exitFragment()
            }
        }
        return super.onOptionsItemSelected(item)
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