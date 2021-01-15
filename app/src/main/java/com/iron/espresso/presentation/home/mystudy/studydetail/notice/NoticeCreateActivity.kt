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

        viewModel.initPin()

        viewModel.pinnedType.observe(this, Observer { pinned ->
            binding.category.apply {
                text = resources.getString(pinned.title)
                setBackgroundResource(pinned.color)
            }
        })

        binding.category.setOnClickListener {
            viewModel.changePinned()
        }

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
                    title = binding.title.text.toString(),
                    contents = binding.content.text.toString()
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