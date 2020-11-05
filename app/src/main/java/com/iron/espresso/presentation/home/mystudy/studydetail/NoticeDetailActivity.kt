package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeDetailBinding
    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TOOLBAR_TITLE)
        }

    }

    companion object {
        const val TOOLBAR_TITLE = "공지사항"
        fun getInstance(context: Context) =
            Intent(context, NoticeDetailActivity::class.java)
    }
}