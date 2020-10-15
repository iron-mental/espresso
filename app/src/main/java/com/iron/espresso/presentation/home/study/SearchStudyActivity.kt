package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding
import kotlinx.android.synthetic.main.activity_search_study.*

class SearchStudyActivity : BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hotKeywordList = arrayListOf<HotKeywordItem>()
        hotKeywordList.add(HotKeywordItem("1"))
        hotKeywordList.add(HotKeywordItem("2"))
        hotKeywordList.add(HotKeywordItem("3"))
        hotKeywordList.add(HotKeywordItem("4"))
        hotKeywordList.add(HotKeywordItem("5"))
        hotKeywordList.add(HotKeywordItem("6"))

        binding.hotKeywordRecyclerview.layoutManager = GridLayoutManager(this,3)
        binding.hotKeywordRecyclerview.adapter = HotKeywordAdapter(hotKeywordList)
    }
}