package com.iron.espresso.presentation.home.study.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyListActivity :
    BaseActivity<ActivityStudyListBinding>(R.layout.activity_study_list) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(getString(R.string.study))
        setNavigationIcon(R.drawable.ic_back_24)

        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(
                R.id.study_list_container,
                NewListFragment.newInstance(),
                resources.getString(R.string.recency)
            ).commit()

        val studyTabList = resources.getStringArray(R.array.study_tab)
        studyTabList.forEach { title ->
            binding.topTab.addTab(binding.topTab.newTab().setText(title))
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                val addFragment = getFragment(tab?.position ?: 0)

                val hideFragment = fragmentManager.fragments.find {
                    if (tab?.position == 0) {
                        it is LocationFragment
                    } else {
                        it is NewListFragment
                    }
                }
                if (hideFragment != null) {
                    fragmentManager.beginTransaction()
                        .hide(hideFragment).commit()
                }

                val findFragment = fragmentManager.findFragmentByTag("${tab?.text}")
                if (findFragment == null) {
                    fragmentManager.beginTransaction()
                        .add(R.id.study_list_container, addFragment, "${tab?.text}").commit()
                } else {
                    fragmentManager.beginTransaction().show(findFragment).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewListFragment.newInstance()
            1 -> LocationFragment.newInstance()
            else -> error("")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyListActivity::class.java)
    }
}