package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivityStudyListBinding
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import com.iron.espresso.presentation.home.study.model.StudyListItem
import kotlinx.android.synthetic.main.activity_study_list.*

class StudyListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyListBinding
    private lateinit var toolbarHelper: ToolbarHelper
    private val studyListAdapter = StudyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_list)
        binding.lifecycleOwner = this

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(getString(R.string.study))
            setNavigationIcon(R.drawable.ic_back_24)
        }

        val studyTabList = resources.getStringArray(R.array.home_tab)
        studyTabList.forEach { title ->
            binding.topTab.addTab(binding.topTab.newTab().setText(title))
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@StudyListActivity, tab?.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val studyList = mutableListOf<StudyListItem>().apply {
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))

        }

        binding.studyList.adapter = studyListAdapter
        studyListAdapter.apply {
            setItemList(studyList)
            itemClickListener = { title ->
                Toast.makeText(this@StudyListActivity, "onClick title: $title", Toast.LENGTH_SHORT)
                    .show()
            }
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