package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityMystudyDetailBinding
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.mystudy.studydetail.ChattingFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.StudyInfoFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.notice.NoticeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudyDetailActivity :
    BaseActivity<ActivityMystudyDetailBinding>(R.layout.activity_mystudy_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(intent.getStringExtra(TOOLBAR_TITLE))
        setNavigationIcon(R.drawable.ic_back_24)

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
                        0 -> NoticeFragment.newInstance(intent.getIntExtra(STUDY_ID, DEFAULT_VALUE))
                        1 -> StudyInfoFragment.newInstance(
                            intent.getIntExtra(
                                STUDY_ID,
                                DEFAULT_VALUE
                            )
                        )
                        2 -> ChattingFragment.newInstance()
                        else -> error("Invalid position")
                    }
            }
        }

        TabLayoutMediator(binding.topTab, binding.studyDetailPager) { tab, position ->
            tab.text = studyDetailTabList[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_mystudy_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.leave_study -> {
                toast("나가기 테스트")
            }
        }
        return true
    }

    companion object {
        private const val TOOLBAR_TITLE = "title"
        const val DEFAULT_VALUE = 0
        const val STUDY_ID = "studyId"
        fun getInstance(context: Context, title: String, id: Int) =
            Intent(context, MyStudyDetailActivity::class.java)
                .putExtra(TOOLBAR_TITLE, title)
                .putExtra(STUDY_ID, id)
    }
}