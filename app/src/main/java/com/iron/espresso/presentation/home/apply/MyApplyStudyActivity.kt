package com.iron.espresso.presentation.home.apply

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commit
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.ActivityApplyStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplyStudyActivity :
    BaseActivity<ActivityApplyStudyBinding>(R.layout.activity_apply_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(R.id.container, ApplyListFragment.newInstance(ApplyListFragment.Type.MY))
        }
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