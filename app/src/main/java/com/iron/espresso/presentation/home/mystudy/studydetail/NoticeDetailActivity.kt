package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeDetailBinding
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.DEFAULT_VALUE
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.STUDY_ID


class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    private val viewModel by viewModels<NoticeDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TOOLBAR_TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        val studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)
        val noticeId = intent.getIntExtra(NOTICE_ID, DEFAULT_VALUE)

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
                    when (notice.pinned) {
                        true -> {
                            text = context.getString(R.string.pined_true)
                            setBackgroundResource(R.color.theme_fc813e)
                        }
                        false -> {
                            text = context.getString(R.string.pined_false)
                            setBackgroundResource(R.color.colorCobaltBlue)
                        }
                    }
                }

            }
        })

        binding.deleteButton.setOnClickListener {
            onBackPressed()
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
        private const val NOTICE_ID = "noticeId"
        fun getInstance(context: Context, noticeId: Int?, studyId: Int) =
            Intent(context, NoticeDetailActivity::class.java).apply {
                putExtra(NOTICE_ID, noticeId)
                putExtra(STUDY_ID, studyId)
            }
    }
}