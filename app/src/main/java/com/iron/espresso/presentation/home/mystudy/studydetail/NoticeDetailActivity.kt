package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeDetailBinding
    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TOOLBAR_TITLE)
            setNavigationIcon(R.drawable.ic_back_24)
        }

        binding.category.apply {
            when (intent.extras?.get("type")) {
                NoticeItemType.HEADER -> {
                    text = resources.getString(R.string.pined_true)
                    setBackgroundResource(R.color.theme_fc813e)
                }
                NoticeItemType.ITEM -> {
                    text = resources.getString(R.string.pined_false)
                    setBackgroundResource(R.color.colorCobaltBlue)
                }
                else -> error("error")
            }
        }
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
        fun getInstance(context: Context, noticeItem: NoticeListItem) =
            Intent(context, NoticeDetailActivity::class.java).putExtra("type", noticeItem.type)
    }
}