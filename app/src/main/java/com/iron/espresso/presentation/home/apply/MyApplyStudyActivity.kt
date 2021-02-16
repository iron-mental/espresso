package com.iron.espresso.presentation.home.apply

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityMyApplyStudyBinding
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplyStudyActivity :
    BaseActivity<ActivityMyApplyStudyBinding>(R.layout.activity_my_apply_study) {

    private val viewModel by viewModels<MyApplyStudyViewModel>()

    private val adapter: MyApplyStudyAdapter by lazy {
        MyApplyStudyAdapter {
            toast(it.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(R.string.title_apply_my_study)
        setNavigationIcon(R.drawable.ic_back_24)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            applyList.adapter = adapter
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            applyList.observe(this@MyApplyStudyActivity, { list ->
                adapter.submitList(list)
            })
        }
    }

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

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MyApplyStudyActivity::class.java)
    }
}


