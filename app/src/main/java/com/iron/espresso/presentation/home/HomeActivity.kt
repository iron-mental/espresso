package com.iron.espresso.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityHomeBinding
import com.iron.espresso.presentation.BindingHelper
import com.iron.espresso.presentation.UseBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyFragment
import com.iron.espresso.presentation.home.setting.SettingFragment
import com.iron.espresso.presentation.home.study.StudyFragment

class HomeActivity : AppCompatActivity(), UseBinding<ActivityHomeBinding> {

    override val binding: ActivityHomeBinding
        get() = BindingHelper.getBinding(this, R.layout.activity_home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeTabList = resources.getStringArray(R.array.home_tab)

        binding.run {
            homePager.adapter = object : FragmentStatePagerAdapter(
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

            bottomTab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.homePager
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            bottomTab.setupWithViewPager(homePager)
        }
    }
}