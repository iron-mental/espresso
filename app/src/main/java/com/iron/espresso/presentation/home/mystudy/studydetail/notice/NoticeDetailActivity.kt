package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.AuthorityType
import com.iron.espresso.data.model.NoticeDetailItem
import com.iron.espresso.databinding.ActivityNoticeDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    private val viewModel by viewModels<NoticeDetailViewModel>()
    private var studyId = -1
    private var noticeId = -1
    private lateinit var noticeItem: NoticeDetailItem
    private var authority: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(resources.getString(R.string.notice_detail))
        setNavigationIcon(R.drawable.ic_back_24)

        studyId =
            intent.getIntExtra(KEY_STUDY_ID, KEY_DEFAULT_VALUE)
        noticeId = intent.getIntExtra(NOTICE_ID, KEY_DEFAULT_VALUE)
        authority = intent.getStringExtra(KEY_AUTHORITY)

        viewModel.showNotice(studyId, noticeId)

        viewModel.notice.observe(this, { notice ->
            binding.run {
                title.text = notice.title
                writerName.text = notice.leaderNickname
                content.text = notice.contents
                date.text = notice.updatedAt

                if (!notice.leaderImage.isNullOrEmpty()) {
                    writerImage.setCircleImage(notice.leaderImage)
                }

                category.apply {
                    if (notice.pinned) {
                        text = context.getString(R.string.pined_true)
                        backgroundTintList = resources.getColorStateList(R.color.theme_fc813e, null)
                    } else {
                        text = context.getString(R.string.normal)
                        backgroundTintList = resources.getColorStateList(R.color.colorCobaltBlue, null)

                    }
                }
                noticeItem = notice
            }
        })

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == resources.getString(R.string.delete_notice)) {
                setResult(RESULT_OK)
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_notice_modify, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (authority == AuthorityType.HOST.authority) {
            menu?.findItem(R.id.modify_notice)?.isVisible = true
            menu?.findItem(R.id.delete_notice)?.isVisible = true
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.modify_notice -> {
                startActivityForResult(
                    NoticeModifyActivity.getIntent(
                        this,
                        studyId,
                        noticeItem
                    ), REQUEST_MODIFY_CODE
                )
            }
            R.id.delete_notice -> {
                showNoticeDeleteDialog()
            }
        }
        return true
    }

    private fun showNoticeDeleteDialog() {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_notice_delete_title))

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == Activity.RESULT_OK) {
                viewModel.deleteNotice(studyId, noticeId)
            }
        }
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
        private const val NOTICE_ID = "noticeId"
        private const val KEY_AUTHORITY = "AUTHORITY"
        private const val KEY_STUDY_ID = "STUDY_ID"
        private const val KEY_DEFAULT_VALUE = -1

        fun getIntent(context: Context, noticeId: Int?, studyId: Int, authority: String?) =
            Intent(context, NoticeDetailActivity::class.java).apply {
                putExtra(NOTICE_ID, noticeId)
                putExtra(KEY_STUDY_ID, studyId)
                putExtra(KEY_AUTHORITY, authority)
            }
    }
}