package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeCreateBinding
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity

class NoticeCreateActivity :
    BaseActivity<ActivityNoticeCreateBinding>(R.layout.activity_notice_create) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("공지사항 작성 화면")
        setNavigationIcon(R.drawable.ic_back_24)

        binding.category.setOnClickListener {
            binding.category.apply {
                if (text.toString() == context.getString(R.string.pined_false)) {
                    text = context.getString(R.string.pined_true)
                    setBackgroundResource(R.color.theme_fc813e)
                } else {
                    text = context.getString(R.string.pined_false)
                    setBackgroundResource(R.color.colorCobaltBlue)
                }
            }
        }
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
                Toast.makeText(this, "공지사항 작성이 완료됐습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, NoticeCreateActivity::class.java)
    }
}