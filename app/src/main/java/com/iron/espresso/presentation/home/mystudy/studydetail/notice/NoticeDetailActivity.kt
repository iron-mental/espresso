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
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.databinding.ActivityNoticeDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity.Companion.DEFAULT_VALUE
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity.Companion.STUDY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    private val viewModel by viewModels<NoticeDetailViewModel>()
    private var studyId = -1
    private var noticeId = -1
    private lateinit var noticeItem: NoticeItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TOOLBAR_TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)
        noticeId = intent.getIntExtra(NOTICE_ID, DEFAULT_VALUE)

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

                category.apply {
                    if (notice.pinned != null && notice.pinned) {
                        text = context.getString(R.string.pined_true)
                        setBackgroundResource(R.color.theme_fc813e)
                    } else {
                        text = context.getString(R.string.pined_false)
                        setBackgroundResource(R.color.colorCobaltBlue)
                    }
                }
                noticeItem = NoticeItem(
                    title.text.toString(),
                    content.text.toString(),
                    notice.pinned ?: false
                )

            }
        })

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        binding.deleteButton.setOnClickListener {
            viewModel.deleteNotice(studyId, noticeId)
            setResult(RESULT_OK)
            onBackPressed()
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
                        noticeId,
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
        const val REQUEST_MODIFY_CODE = 3
        const val TOOLBAR_TITLE = "공지사항 상세 화면"
        const val NOTICE_ID = "noticeId"
        fun getInstance(context: Context, noticeId: Int?, studyId: Int) =
            Intent(context, NoticeDetailActivity::class.java).apply {
                putExtra(NOTICE_ID, noticeId)
                putExtra(STUDY_ID, studyId)
            }
    }
}