package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editText = EditText(this@SearchStudyActivity)
        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setCustomView(editText.apply {
                hint = context.getString(R.string.search_hint)
                maxLines = 1
            })
            setNavigationIcon(R.drawable.ic_back_24)
            setTitle("")
        }


        val hotKeywordList = arrayListOf<HotKeywordItem>()
        hotKeywordList.add(HotKeywordItem("안드로이드"))
        hotKeywordList.add(HotKeywordItem("node.js"))
        hotKeywordList.add(HotKeywordItem("코드리뷰fdsafdsafdsafdsafdsafdsafd"))
        hotKeywordList.add(HotKeywordItem("취업스터디"))
        hotKeywordList.add(HotKeywordItem("프로젝트"))
        hotKeywordList.add(HotKeywordItem("Swift"))
        hotKeywordList.add(HotKeywordItem("안드로이드fdsafdsafdsafdsafdsafdsa"))
        hotKeywordList.add(HotKeywordItem("node.rkdcjfajdcjddl"))
        hotKeywordList.add(HotKeywordItem("코드리뷰"))
        hotKeywordList.add(HotKeywordItem("취업스터디"))
        hotKeywordList.add(HotKeywordItem("프로젝트fdsafdsafdsafdsafdsa"))
        hotKeywordList.add(HotKeywordItem("Swift"))

        for (keyWord in hotKeywordList) {
            val button = Chip(this).apply {
                text = keyWord.title
                setOnClickListener {
                    Toast.makeText(this@SearchStudyActivity, text, Toast.LENGTH_SHORT).show()
                    editText.setText(text)
                }
            }
            binding.hotKeywordGroup.addView(button)
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