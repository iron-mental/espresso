package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeCreateBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity.Companion.DEFAULT_VALUE
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity.Companion.STUDY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeCreateActivity :
    BaseActivity<ActivityNoticeCreateBinding>(R.layout.activity_notice_create) {

    private val viewModel by viewModels<NoticeCreateViewModel>()
    private var studyId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(resources.getString(R.string.notice_create))
        setNavigationIcon(R.drawable.ic_back_24)

        studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)

        binding.categoryContainer.getTabAt(1)?.select()
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

        viewModel.emptyCheckMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, resources.getString(message.resId), Toast.LENGTH_SHORT).show()
            if (message == ValidationInputText.REGISTER_NOTICE) {
                setResult(RESULT_OK)
                finish()
            }
        })

        viewModel.toastMessage.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
                viewModel.createNotice(
                    studyId = studyId,
                    title = binding.titleInputView.text.toString(),
                    contents = binding.contentInputView.text.toString()
                )
            }
        }
        return true
    }

    companion object {
        fun getIntent(context: Context, studyId: Int) =
            Intent(context, NoticeCreateActivity::class.java).apply {
                putExtra(STUDY_ID, studyId)
            }
    }
}