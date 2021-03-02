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
class ApplyStudyActivity :
    BaseActivity<ActivityApplyStudyBinding>(R.layout.activity_apply_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(
                R.id.container,
                ApplyListFragment.newInstance(
                    ApplyListFragment.Type.NONE,
                    intent.getIntExtra(KEY_STUDY_ID, -1)
                )
            )
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
        private const val KEY_STUDY_ID = "STUDY_ID"
        fun getIntent(context: Context, studyId: Int): Intent =
            Intent(context, ApplyStudyActivity::class.java)
                .putExtra(KEY_STUDY_ID, studyId)
    }
}