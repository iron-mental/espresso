package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeModifyBinding

class NoticeModifyActivity :
    BaseActivity<ActivityNoticeModifyBinding>(R.layout.activity_notice_modify) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("공지사항 수정 화면")
        setNavigationIcon(R.drawable.ic_back_24)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_notice_create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.create_notice -> {
                Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()

            }
        }
        return true
    }


    companion object {
        fun getInstance(context: Context) =
            Intent(context, NoticeModifyActivity::class.java)
    }
}