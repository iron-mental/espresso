package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class SearchStudyViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

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

    fun showSearchStudyList(word: String) {
        compositeDisposable += studyApi
            .getSearchStudyList(
                bearerToken = AuthHolder.bearerToken,
                word = word
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }
}