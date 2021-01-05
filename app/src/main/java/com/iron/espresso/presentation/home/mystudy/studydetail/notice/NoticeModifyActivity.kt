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
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.databinding.ActivityNoticeModifyBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeModifyActivity :
    BaseActivity<ActivityNoticeModifyBinding>(R.layout.activity_notice_modify) {

    private val viewModel by viewModels<NoticeModifyViewModel>()
    private var studyId = -1
    private var noticeId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("공지사항 수정 화면")
        setNavigationIcon(R.drawable.ic_back_24)

        studyId =
            intent.getIntExtra(StudyDetailActivity.STUDY_ID, StudyDetailActivity.DEFAULT_VALUE)
        noticeId =
            intent.getIntExtra(NoticeDetailActivity.NOTICE_ID, StudyDetailActivity.DEFAULT_VALUE)
        val noticeItem = intent.getSerializableExtra(NOTICE_ITEM) as NoticeItem

        binding.run {
            title.setText(noticeItem.title)
            content.setText(noticeItem.contents)
        }

        viewModel.initPin(noticeItem.pinned ?: false)

        viewModel.pinnedType.observe(this, Observer { pinned ->

            binding.category.apply {
                text = resources.getString(pinned.title)
                setBackgroundResource(pinned.color)
            }
        })

        binding.category.setOnClickListener {
            viewModel.changePinned()
        }

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.snackBarMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, resources.getString(message.resId), Toast.LENGTH_SHORT).show()
            if (message == ValidationInputText.MODIFY_NOTICE) {
                setResult(RESULT_OK)
                finish()
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
                    studyId, noticeId,
                    binding.title.text.toString(),
                    binding.content.text.toString()
                )
            }
        }
        return true
    }


    companion object {
        private const val NOTICE_ITEM = "noticeItem"
        fun getInstance(context: Context, studyId: Int, noticeId: Int, noticeItem: NoticeItem) =
            Intent(context, NoticeModifyActivity::class.java).apply {
                putExtra(StudyDetailActivity.STUDY_ID, studyId)
                putExtra(NoticeDetailActivity.NOTICE_ID, noticeId)
                putExtra(NOTICE_ITEM, noticeItem)
            }
    }
}