package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityModifyStudyBinding

class ModifyStudyActivity :
    BaseActivity<ActivityModifyStudyBinding>(R.layout.activity_modify_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, ModifyStudyActivity::class.java)
    }
}
