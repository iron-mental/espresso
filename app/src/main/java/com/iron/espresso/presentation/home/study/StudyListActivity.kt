package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivityStudyListBinding
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import com.iron.espresso.presentation.home.study.model.StudyListItem

class StudyListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyListBinding

    lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_list)
        binding.lifecycleOwner = this

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(getString(R.string.study))
            setNavigationIcon(R.drawable.ic_back_24)
        }

        val studyList = mutableListOf<StudyListItem>().apply {
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))
            add(StudyListItem("안드로이드 스터디", "강남역 윙스터디", "강남구", "10/27", "", ""))

        }

        binding.studyList.adapter = StudyListAdapter(studyList)


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