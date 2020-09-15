package com.iron.espresso.presentation.home

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityHomeBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyFragment
import com.iron.espresso.presentation.home.setting.SettingFragment
import com.iron.espresso.presentation.home.study.StudyFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        val homeTabList = resources.getStringArray(R.array.home_tab)


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

        Handler().postDelayed({
            binding.bottomTab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_settings_24)
            binding.bottomTab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_settings_24)
            binding.bottomTab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_settings_24)
        }, 100)
    }
}