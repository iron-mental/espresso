package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityStudyListBinding

class StudyListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_list)
        binding.lifecycleOwner = this
    }

    companion object {
        fun getInstance(context: Context) =
                Intent(context, StudyListActivity::class.java)
    }
}