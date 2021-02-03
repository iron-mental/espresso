package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStudyActivity :
    BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_back_24)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.search_frg_container,
                SearchFragment.newInstance()
            )
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, SearchStudyActivity::class.java)
    }
}