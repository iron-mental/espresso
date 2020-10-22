package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding

class SearchStudyActivity :
        BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    private lateinit var toolbarHelper: ToolbarHelper
    private lateinit var searchEditText: EditText
    private lateinit var hotKeywordButton: Chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchEditText = EditText(this)
        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setCustomView(searchEditText.apply {
                hint = context.getString(R.string.search_hint)
                maxLines = 1
            })
            setNavigationIcon(R.drawable.ic_back_24)
            setTitle("")
        }

        binding.placeSearchButton.setOnClickListener {
            Toast.makeText(this, binding.placeSearchButton.text, Toast.LENGTH_SHORT).show()
        }

        val hotKeywordList = arrayListOf<HotKeywordItem>().apply {
            add(HotKeywordItem("안드로이드"))
            add(HotKeywordItem("node.js"))
            add(HotKeywordItem("코드리뷰fdsafdsafdsafdsafdsafdsafd"))
            add(HotKeywordItem("취업스터디"))
            add(HotKeywordItem("프로젝트"))
            add(HotKeywordItem("Swift"))
            add(HotKeywordItem("안드로이드fdsafdsafdsafdsafdsafdsa"))
            add(HotKeywordItem("node.rkdcjfajdcjddl"))
            add(HotKeywordItem("코드리뷰"))
            add(HotKeywordItem("취업스터디"))
            add(HotKeywordItem("프로젝트fdsafdsafdsafdsafdsa"))
            add(HotKeywordItem("Swift"))
        }

        // 핫 키워드 버튼 클릭 시 검색 창 text 대응
        hotKeywordList.forEach { keyWord ->
            hotKeywordButton = Chip(this).apply {
                text = keyWord.title
                setOnClickListener {
                    Toast.makeText(this@SearchStudyActivity, text, Toast.LENGTH_SHORT).show()
                    searchEditText.setText(text)
                }
            }
            binding.hotKeywordGroup.addView(hotKeywordButton)
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
}