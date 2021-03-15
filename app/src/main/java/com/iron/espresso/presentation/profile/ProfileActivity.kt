package com.iron.espresso.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationIcon(R.drawable.ic_back_24)

        val fragment = ProfileFragment.newInstance()
        supportFragmentManager.commit {
            replace(R.id.edit_frag_container, fragment, fragment::class.java.simpleName)
        }

        supportFragmentManager.setFragmentResultListener(
            ProfileFragment.KEY_UPDATE_PROFILE,
            this
        ) { _, _ ->
            setResult(RESULT_OK)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.edit_frag_container)

        when {
            fragment is ProfileFragment -> finish()
            fragment != null -> {
                supportFragmentManager.commitNow {
                    remove(fragment)
                }
                val profileFragment = supportFragmentManager.fragments.find { it is ProfileFragment }
                if (profileFragment != null) {
                    supportFragmentManager.commit {
                        show(profileFragment)
                    }
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {

            }
        }
        return false
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, ProfileActivity::class.java)
    }
}