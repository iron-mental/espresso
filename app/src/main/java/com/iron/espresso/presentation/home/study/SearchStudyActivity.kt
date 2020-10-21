package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding

class SearchStudyActivity :
        BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setCustomView(EditText(this@SearchStudyActivity).apply {
                hint = "스터디명 분류(키워드) 등"
                maxLines = 1
                tag = "iron"
            })
            setNavigationIcon(R.drawable.ic_back_24)
            setTitle("")
        }


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
}