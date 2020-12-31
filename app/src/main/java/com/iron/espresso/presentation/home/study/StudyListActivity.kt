package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyListBinding
import com.iron.espresso.presentation.home.study.list.LocationFragment
import com.iron.espresso.presentation.home.study.list.NewListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyListActivity :
    BaseActivity<ActivityStudyListBinding>(R.layout.activity_study_list) {

    private var newListFragment: Fragment? = null
    private var locationFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(getString(R.string.study))
        setNavigationIcon(R.drawable.ic_back_24)

        val fragmentManager = supportFragmentManager

        newListFragment = NewListFragment.newInstance()
        fragmentManager.beginTransaction()
            .replace(R.id.study_list_container, newListFragment!!).commit()

        val studyTabList = resources.getStringArray(R.array.study_tab)
        studyTabList.forEach { title ->
            binding.topTab.addTab(binding.topTab.newTab().setText(title))
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        if (newListFragment == null) {
                            newListFragment = NewListFragment.newInstance()
                            fragmentManager.beginTransaction()
                                .add(R.id.study_list_container, newListFragment!!).commit()
                        }

                        if (newListFragment != null) {
                            fragmentManager.beginTransaction()
                                .show(newListFragment!!).commit()
                        }
                        if (locationFragment != null) {
                            fragmentManager.beginTransaction()
                                .hide(locationFragment!!).commit()
                        }
                    }
                    1 -> {
                        if (locationFragment == null) {
                            locationFragment = LocationFragment.newInstance()
                            fragmentManager.beginTransaction()
                                .add(R.id.study_list_container, locationFragment!!).commit()
                        }

                        if (newListFragment != null) {
                            fragmentManager.beginTransaction()
                                .hide(newListFragment!!).commit()
                        }
                        if (locationFragment != null) {
                            fragmentManager.beginTransaction()
                                .show(locationFragment!!).commit()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
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