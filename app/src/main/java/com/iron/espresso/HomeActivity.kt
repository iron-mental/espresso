package com.iron.espresso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iron.espresso.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        val homeTabList = listOf("스터디", "내 스터디", "설정")


        binding.homePager.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int =
                homeTabList.size

            override fun getItem(position: Int): Fragment =
                when (position) {
                    0 -> StudyFragment.newInstance()
                    1 -> MyStudyFragment.newInstance()
                    2 -> SettingFragment.newInstance()
                    else -> error("Invalid position")
                }

            override fun getPageTitle(position: Int): CharSequence? =
                homeTabList[position]
        }

        binding.bottomTab.setupWithViewPager(binding.homePager)
    }
}