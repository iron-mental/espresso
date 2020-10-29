package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.presentation.home.mystudy.studydetail.ChattingFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.NoticeFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.StudyInfoFragment

class StudyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_detail)

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
                        0 -> NoticeFragment.newInstance()
                        1 -> StudyInfoFragment.newInstance()
                        2 -> ChattingFragment.newInstance()
                        else -> error("Invalid position")
                    }
            }
        }

        TabLayoutMediator(binding.topTab, binding.studyDetailPager) { tab, position ->
            tab.text = studyDetailTabList[position]
        }.attach()
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}