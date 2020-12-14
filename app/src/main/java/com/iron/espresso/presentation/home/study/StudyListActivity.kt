package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyListBinding
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter

class StudyListActivity :
    BaseActivity<ActivityStudyListBinding>(R.layout.activity_study_list) {

    private val viewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(getString(R.string.study))
        setNavigationIcon(R.drawable.ic_back_24)

        val studyTabList = resources.getStringArray(R.array.study_tab)
        studyTabList.forEach { title ->
            binding.topTab.addTab(binding.topTab.newTab().setText(title))
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getStudyList("android", checkTab(tab?.text.toString()))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.getStudyList("android", "new")

        viewModel.studyList.observe(this, Observer { studyList ->

            studyListAdapter.apply {
                setItemList(studyList)
                itemClickListener = { title ->
                    Toast.makeText(
                        this@StudyListActivity,
                        "onClick title: $title",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

        binding.studyList.adapter = studyListAdapter

    }

    private fun checkTab(tab: String): String {
        return if (tab == "최신") {
            "new"
        } else {
            "length"
        }
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
            Intent(context, StudyListActivity::class.java)
    }
}