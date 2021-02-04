package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityEditMyStudyBinding

class EditMyStudyActivity :
    BaseActivity<ActivityEditMyStudyBinding>(R.layout.activity_edit_my_study) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, EditMyStudyActivity::class.java)
    }
}