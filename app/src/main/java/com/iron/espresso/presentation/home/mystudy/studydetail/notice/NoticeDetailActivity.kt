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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.NoticeDetailItem
import com.iron.espresso.databinding.ActivityNoticeDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    private val viewModel by viewModels<NoticeDetailViewModel>()
    private var studyId = -1
    private var noticeId = -1
    private lateinit var noticeItem: NoticeDetailItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TOOLBAR_TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        studyId =
            intent.getIntExtra(StudyDetailActivity.STUDY_ID, StudyDetailActivity.DEFAULT_VALUE)
        noticeId = intent.getIntExtra(NOTICE_ID, StudyDetailActivity.DEFAULT_VALUE)

        viewModel.showNotice(studyId, noticeId)

        viewModel.notice.observe(this, Observer { notice ->
            binding.run {
                title.text = notice.title
                writerName.text = notice.leaderNickname
                content.text = notice.contents
                date.text = notice.updatedAt

                Glide.with(this@NoticeDetailActivity)
                    .load(notice.leaderImage)
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.dummy_image)
                    .into(writerImage)

                if (notice.pinned != null) {
                    viewModel.initPin(notice.pinned)

                    noticeItem = notice
                }
            }
        })

        viewModel.pinnedType.observe(this, Observer { pinned ->
            binding.category.apply {
                text = resources.getString(pinned.title)
                setBackgroundResource(pinned.color)
            }
        })

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        binding.deleteButton.setOnClickListener {
            viewModel.deleteNotice(studyId, noticeId)
            setResult(RESULT_OK)
            finish()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_notice_modify, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.modify_notice -> {
                startActivityForResult(
                    NoticeModifyActivity.getInstance(
                        this,
                        studyId,
                        noticeItem
                    ), REQUEST_MODIFY_CODE
                )
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_MODIFY_CODE && resultCode == RESULT_OK) {
            viewModel.showNotice(studyId, noticeId)
            setResult(RESULT_OK)
        }
    }

    companion object {
        private const val REQUEST_MODIFY_CODE = 3
        private const val TOOLBAR_TITLE = "공지사항 상세 화면"
        const val NOTICE_ID = "noticeId"
        fun getInstance(context: Context, noticeId: Int?, studyId: Int) =
            Intent(context, NoticeDetailActivity::class.java).apply {
                putExtra(NOTICE_ID, noticeId)
                putExtra(StudyDetailActivity.STUDY_ID, studyId)
            }
    }
}