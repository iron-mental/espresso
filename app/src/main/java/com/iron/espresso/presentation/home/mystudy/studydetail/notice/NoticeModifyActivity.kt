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
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.NoticeDetailItem
import com.iron.espresso.databinding.ActivityNoticeModifyBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeModifyActivity :
    BaseActivity<ActivityNoticeModifyBinding>(R.layout.activity_notice_modify) {

    private val viewModel by viewModels<NoticeModifyViewModel>()
    private var studyId = -1
    private var noticeId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(resources.getString(R.string.notice_modify))
        setNavigationIcon(R.drawable.ic_back_24)

        studyId =
            intent.getIntExtra(MyStudyDetailActivity.STUDY_ID, MyStudyDetailActivity.DEFAULT_VALUE)

        val noticeItem = intent.getSerializableExtra(NOTICE_ITEM) as NoticeDetailItem
        noticeId = noticeItem.id

        initSelectCategory(noticeItem.pinned)
        binding.categoryContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.categoryContainer.setSelectedTabIndicatorColor(getColor(R.color.theme_fc813e))
                        viewModel.changePinned()
                    }
                    1 -> {
                        binding.categoryContainer.setSelectedTabIndicatorColor(getColor(R.color.colorCobaltBlue))
                        viewModel.changePinned()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.run {
            titleInputView.setText(noticeItem.title)
            contentInputView.setText(noticeItem.contents)
        }

        viewModel.initPin(noticeItem.pinned)

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.emptyCheckMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, resources.getString(message.resId), Toast.LENGTH_SHORT).show()
            if (message == ValidationInputText.MODIFY_NOTICE) {
                setResult(RESULT_OK)
                finish()
            }
        })
    }

    private fun initSelectCategory(pinned: Boolean) {
        if (pinned) {
            binding.categoryContainer.run {
                getTabAt(0)?.select()
                setSelectedTabIndicatorColor(getColor(R.color.theme_fc813e))
            }
        } else {
            binding.categoryContainer.run {
                getTabAt(1)?.select()
                setSelectedTabIndicatorColor(getColor(R.color.colorCobaltBlue))
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
            R.id.complete -> {
                showNoticeModifyDialog()
            }
        }
        return true
    }

    private fun showNoticeModifyDialog() {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_notice_modify_title))

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == Activity.RESULT_OK) {
                viewModel.modifyNotice(
                    studyId,
                    noticeId,
                    binding.titleInputView.text.toString(),
                    binding.contentInputView.text.toString()
                )
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.find { it is ConfirmDialog }

        if (fragment != null) {
            supportFragmentManager.commit {
                remove(fragment)
            }
        } else {
            return super.onBackPressed()
        }
    }


    companion object {
        private const val NOTICE_ITEM = "noticeItem"
        fun getIntent(
            context: Context,
            studyId: Int,
            noticeItem: NoticeDetailItem
        ) =
            Intent(context, NoticeModifyActivity::class.java).apply {
                putExtra(MyStudyDetailActivity.STUDY_ID, studyId)
                putExtra(NOTICE_ITEM, noticeItem)
            }
    }
}