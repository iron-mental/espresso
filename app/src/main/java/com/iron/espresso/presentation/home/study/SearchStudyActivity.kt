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

class SearchStudyActivity :
    BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hotKeywordList = arrayListOf<HotKeywordItem>()
        hotKeywordList.add(HotKeywordItem("안드로이드"))
        hotKeywordList.add(HotKeywordItem("node.js"))
        hotKeywordList.add(HotKeywordItem("코드리뷰"))
        hotKeywordList.add(HotKeywordItem("취업스터디"))
        hotKeywordList.add(HotKeywordItem("프로젝트"))
        hotKeywordList.add(HotKeywordItem("Swift"))

        binding.hotKeywordRecyclerview.layoutManager = GridLayoutManager(this, 3)
        binding.hotKeywordRecyclerview.adapter = HotKeywordAdapter(hotKeywordList)
    }
}