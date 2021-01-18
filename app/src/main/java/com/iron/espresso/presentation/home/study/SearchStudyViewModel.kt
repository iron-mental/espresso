package com.iron.espresso.presentation.home.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel

class SearchStudyViewModel : BaseViewModel() {

    private val _hotKeywordList = MutableLiveData<List<HotKeywordItem>>()
    val hotKeywordList: LiveData<List<HotKeywordItem>>
        get() = _hotKeywordList

    fun getHotKeywordList() {
        val hotKeywordItemList = arrayListOf<HotKeywordItem>().apply {
            add(HotKeywordItem("프로젝트"))
            add(HotKeywordItem("Swift"))
            add(HotKeywordItem("안드로이드fdsafdsafdsafdsafdsafdsa"))
            add(HotKeywordItem("node.rkdcjfajdcjddl"))
            add(HotKeywordItem("코드리뷰"))
            add(HotKeywordItem("안드로이드"))
            add(HotKeywordItem("node.js"))
            add(HotKeywordItem("코드리뷰fdsafdsafdsafdsafdsafdsafd"))
            add(HotKeywordItem("취업스터디"))
            add(HotKeywordItem("취업스터디"))
            add(HotKeywordItem("프로젝트fdsafdsafdsafdsafdsa"))
            add(HotKeywordItem("Swift"))
        }

        _hotKeywordList.value = hotKeywordItemList
    }
}