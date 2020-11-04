package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityStudyDetailBinding

class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)
    }
}