package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.databinding.ActivityNoticeModifyBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.DEFAULT_VALUE
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.STUDY_ID
import com.iron.espresso.presentation.home.mystudy.studydetail.notice.NoticeDetailActivity.Companion.NOTICE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeModifyActivity :
    BaseActivity<ActivityNoticeModifyBinding>(R.layout.activity_notice_modify) {

    private val viewModel by viewModels<NoticeModifyViewModel>()
    private var studyId = -1
    private var noticeId = -1
    private var pinned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("공지사항 수정 화면")
        setNavigationIcon(R.drawable.ic_back_24)

        studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)
        noticeId = intent.getIntExtra(NOTICE_ID, DEFAULT_VALUE)
        val noticeItem = intent.getSerializableExtra(NOTICE_ITEM) as NoticeItem

        binding.run {
            title.setText(noticeItem.title)
            content.setText(noticeItem.contents)
        }

        viewModel.pinnedCheck(noticeItem.pinned)

        viewModel.pinned.observe(this, Observer { pinned ->
            this.pinned = pinned.pinned

            binding.category.apply {
                text = resources.getString(pinned.title)
                setBackgroundResource(pinned.color)
            }
        })

        binding.category.setOnClickListener {
            viewModel.changePinned(pinned)
        }

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == "임시 공지사항 수정 성공") {
                setResult(RESULT_OK)
                onBackPressed()
            }
        })
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

                viewModel.modifyNotice(
                    studyId, noticeId, NoticeItem(
                        binding.title.text.toString(),
                        binding.content.text.toString(),
                        pinned
                    )
                )
            }
        }
        return true
    }


    companion object {
        private const val NOTICE_ITEM = "noticeItem"
        fun getInstance(context: Context, studyId: Int, noticeId: Int, noticeItem: NoticeItem) =
            Intent(context, NoticeModifyActivity::class.java).apply {
                putExtra(STUDY_ID, studyId)
                putExtra(NOTICE_ID, noticeId)
                putExtra(NOTICE_ITEM, noticeItem)
            }
    }
}