package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding

class StudyDetailActivity :
    BaseActivity<ActivityStudyDetailBinding>(R.layout.activity_study_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}