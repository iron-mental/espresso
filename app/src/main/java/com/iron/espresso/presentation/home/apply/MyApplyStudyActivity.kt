package com.iron.espresso.presentation.home.apply

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.ActivityMyApplyStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplyStudyActivity :
    BaseActivity<ActivityMyApplyStudyBinding>(R.layout.activity_my_apply_study) {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        if ((fragment as? BaseFragment<*>)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MyApplyStudyActivity::class.java)
    }
}


