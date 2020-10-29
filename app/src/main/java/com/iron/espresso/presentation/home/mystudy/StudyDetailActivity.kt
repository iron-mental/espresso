package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.presentation.home.mystudy.studydetail.NoticeFragment

class StudyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_detail)

        val testButton = Button(this).apply {
            text = "스터디 상세"
            setOnClickListener {
                binding.containerNotice.bringToFront()
                supportFragmentManager.beginTransaction()
                    .add(R.id.container_notice, NoticeFragment()).commit()
            }
        }
        (binding.root as ViewGroup).addView(testButton)
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}