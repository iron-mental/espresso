package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityCreateStudyBinding

class StudyCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_study)
        binding.lifecycleOwner = this

    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyCreateActivity::class.java)
    }
}