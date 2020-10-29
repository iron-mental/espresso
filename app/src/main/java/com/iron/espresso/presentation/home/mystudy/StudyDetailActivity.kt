package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityStudyDetailBinding

class StudyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_detail)
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}