package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeDetailBinding


class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TOOLBAR_TITLE)
        setNavigationIcon(R.drawable.ic_back_24)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {
        const val TOOLBAR_TITLE = "공지사항 상세 화면"
        fun getInstance(context: Context) =
            Intent(context, NoticeDetailActivity::class.java)
    }
}