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

    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = intent.getStringExtra(STUDY_CATEGORY).orEmpty()
        setToolbarTitle(category)
        setNavigationIcon(R.drawable.ic_back_24)

        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(
                R.id.study_list_container,
                NewListFragment.newInstance(category),
                resources.getString(R.string.recency)
            )
            .commit()

        val studyTabList = resources.getStringArray(R.array.study_tab)
        studyTabList.forEach { title ->
            binding.topTab.addTab(binding.topTab.newTab().setText(title))
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab ?: return
                val findFragment = fragmentManager.findFragmentByTag("${tab.text}")
                if (findFragment == null) {
                    val addFragment = getFragment(tab.position)
                    fragmentManager.beginTransaction()
                        .add(R.id.study_list_container, addFragment, "${tab.text}").commit()
                } else {
                    fragmentManager.beginTransaction()
                        .show(findFragment)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab ?: return
                val hideFragment = fragmentManager.findFragmentByTag("${tab.text}")
                if (hideFragment != null) {
                    fragmentManager.beginTransaction()
                        .hide(hideFragment)
                        .commit()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewListFragment.newInstance(category)
            1 -> LocationFragment.newInstance(category)
            else -> error("Invalid position")
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
        private const val STUDY_CATEGORY = "study_category"
        fun getIntent(context: Context, item: String) =
            Intent(context, StudyListActivity::class.java)
                .putExtra(STUDY_CATEGORY, item)
    }
}