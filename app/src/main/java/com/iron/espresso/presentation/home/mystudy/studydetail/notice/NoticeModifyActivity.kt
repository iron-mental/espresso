package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeModifyBinding

class NoticeModifyActivity :
    BaseActivity<ActivityNoticeModifyBinding>(R.layout.activity_notice_modify) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, NoticeModifyActivity::class.java)
    }
}