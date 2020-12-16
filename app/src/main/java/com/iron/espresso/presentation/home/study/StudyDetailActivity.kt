package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding

class StudyDetailActivity :
    BaseActivity<ActivityStudyDetailBinding>(R.layout.activity_study_detail) {

    private val viewModel by viewModels<StudyDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("스터디 상세화면")
        setNavigationIcon(R.drawable.ic_back_24)

        viewModel.getStudy(193)

        viewModel.studyDetail.observe(this, Observer { studyDetail ->
            binding.introduceDetail.text = studyDetail.introduce
            binding.proceedDetail.text = studyDetail.progress
            binding.timeDetail.text = studyDetail.studyTime
            binding.placeDetail.text = studyDetail.locationResponse?.addressName
        })

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
            Intent(context, StudyDetailActivity::class.java)
    }
}