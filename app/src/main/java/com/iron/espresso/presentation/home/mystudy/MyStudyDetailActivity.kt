package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.data.model.StudyInfoItem
import com.iron.espresso.databinding.ActivityMystudyDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.apply.ApplyStudyActivity
import com.iron.espresso.presentation.home.mystudy.studydetail.ChattingFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.DelegateLeaderActivity
import com.iron.espresso.presentation.home.mystudy.studydetail.ModifyStudyActivity
import com.iron.espresso.presentation.home.mystudy.studydetail.StudyInfoFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.notice.NoticeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudyDetailActivity :
    BaseActivity<ActivityMystudyDetailBinding>(R.layout.activity_mystudy_detail) {

    private val viewModel by viewModels<MyStudyDetailViewModel>()
    private var studyId = -1
    private var studyInfoItem: StudyInfoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(intent.getStringExtra(TOOLBAR_TITLE))
        setNavigationIcon(R.drawable.ic_back_24)

        studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)
        val studyDetailTabList = resources.getStringArray(R.array.study_detail_tab)

        binding.run {
            studyDetailPager.adapter = object : FragmentStateAdapter(
                supportFragmentManager,
                lifecycle
            ) {
                override fun getItemCount(): Int =
                    studyDetailTabList.size

                override fun createFragment(position: Int): Fragment =
                    when (position) {
                        0 -> NoticeFragment.newInstance(studyId)
                        1 -> StudyInfoFragment.newInstance(studyId)
                        2 -> ChattingFragment.newInstance()
                        else -> error("Invalid position")
                    }
            }
        }

        TabLayoutMediator(binding.topTab, binding.studyDetailPager) { tab, position ->
            tab.text = studyDetailTabList[position]
        }.attach()

        viewModel.getStudy(studyId)

        viewModel.studyDetail.observe(this, { studyDetailItem ->
            studyInfoItem = studyDetailItem.studyInfoItem
            setToolbarTitle(studyInfoItem?.title)

            val findFragment = supportFragmentManager.fragments.find { it is NoticeFragment } as NoticeFragment
            findFragment.setAuthority(studyInfoItem?.authority.orEmpty())

        })

        viewModel.toastMessage.observe(this, EventObserver { message ->
            toast(message)
            setResult(RESULT_OK)
            finish()
        })
    }

    private fun checkAuthority(authority: String): Boolean {
        return when (authority) {
            AUTH_HOST -> {
                false
            }
            else -> {
                true
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DELEGATE_CODE && resultCode == RESULT_OK) {
            finish()
            startActivity(intent)
        } else if (requestCode == MODIFY_CODE && resultCode == RESULT_OK) {
            finish()
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_mystudy_detail, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (studyInfoItem?.authority == AUTH_HOST) {
            menu?.findItem(R.id.apply_list)?.isVisible = true
            menu?.findItem(R.id.delete_study)?.isVisible = true
            menu?.findItem(R.id.host_delegate)?.isVisible = true
            menu?.findItem(R.id.modify_study)?.isVisible = true
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.apply_list -> {
                startActivity(ApplyStudyActivity.getIntent(this, studyId))
            }
            R.id.leave_study -> {
                if (checkAuthority(studyInfoItem?.authority ?: "")) {
                    viewModel.leaveStudy(studyId)
                } else {
                    toast(resources.getString(R.string.pass_permission))
                }
            }
            R.id.delete_study -> {
                viewModel.deleteStudy(studyId)
            }
            R.id.host_delegate -> {
                startActivityForResult(
                    DelegateLeaderActivity.getIntent(
                        this,
                        studyId,
                        studyInfoItem?.participateItem as ArrayList<ParticipateItem>
                    ), DELEGATE_CODE
                )
            }
            R.id.modify_study -> {
                val studyInfoItem = this.studyInfoItem
                if (studyInfoItem != null) {
                    startActivityForResult(
                        ModifyStudyActivity.getIntent(this, studyInfoItem),
                        MODIFY_CODE
                    )
                }
            }
            else -> {
                return false
            }
        }
        return true
    }

    companion object {
        private const val TOOLBAR_TITLE = "title"
        const val DEFAULT_VALUE = 0
        const val STUDY_ID = "studyId"
        private const val AUTH_HOST = "host"
        private const val DELEGATE_CODE = 1
        private const val MODIFY_CODE = 2

        fun getInstance(context: Context, title: String, id: Int) =
            Intent(context, MyStudyDetailActivity::class.java)
                .putExtra(TOOLBAR_TITLE, title)
                .putExtra(STUDY_ID, id)
    }
}