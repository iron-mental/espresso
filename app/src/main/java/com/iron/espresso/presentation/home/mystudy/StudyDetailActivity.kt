package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.presentation.home.mystudy.studydetail.ChattingFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.NoticeFragment
import com.iron.espresso.presentation.home.mystudy.studydetail.StudyInfoFragment

class StudyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyDetailBinding
    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_detail)

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(intent.extras?.getString("title"))
            setNavigationIcon(R.drawable.ic_back_24)
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            else -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}