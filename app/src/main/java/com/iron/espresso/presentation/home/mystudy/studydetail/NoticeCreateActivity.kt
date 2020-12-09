package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeCreateBinding

class NoticeCreateActivity :
    BaseActivity<ActivityNoticeCreateBinding>(R.layout.activity_notice_create) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, NoticeCreateActivity::class.java)
    }
}