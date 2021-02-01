package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class SearchStudyViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<StudyItem>>()
    val studyList: LiveData<List<StudyItem>>
        get() = _studyList

    private val _hotKeywordList = MutableLiveData<List<HotKeywordItem>>()
    val hotKeywordList: LiveData<List<HotKeywordItem>>
        get() = _hotKeywordList

    private val allList = mutableListOf<StudyItem>()
    private var pagingSize = -1
    private var isPaging = false

    private fun firstItemResult(studyList: List<StudyItem>): MutableList<StudyItem> {
        val list: MutableList<StudyItem> = mutableListOf()
        allList.clear()
        allList.addAll(studyList)

        studyList.forEach { studyItem ->
            if (studyItem.isPaging) {
                isPaging = true
                return@forEach
            } else {
                list.add(studyItem)
            }
        }
        pagingSize = list.size
        return list
    }

    private fun scrollMoreItem(startSize: Int): List<Int> {
        val scrollIdList = mutableListOf<Int>()
        val endSize = startSize + pagingSize
        return when {
            endSize <= allList.size -> {
                for (i in startSize until endSize) {
                    scrollIdList.add(allList[i].id)
                }
                scrollIdList
            }
            endSize > allList.size -> {
                isPaging = false
                for (i in startSize until allList.size) {
                    scrollIdList.add(allList[i].id)
                }
                scrollIdList
            }
            else -> {
                isPaging = false
                error("validation error")
            }
        }
    }

    fun showSearchStudyList(word: String) {
        compositeDisposable += studyApi
            .getSearchStudyList(
                bearerToken = AuthHolder.bearerToken,
                word = word
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyList.value =
                        firstItemResult(
                            it.data.map { studyResponse ->
                                studyResponse.toStudyItem()
                            }
                        )
                }
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }

    fun getSearchStudyListPaging(sort: String, itemCount: Int) {
        if (isPaging) {
            compositeDisposable += studyApi
                .getStudyPagingList(
                    sort = sort,
                    studyIds = scrollMoreItem(itemCount).joinToString(",")
                )
                .networkSchedulers()
                .subscribe({
                    _studyList.value = _studyList.value?.plus(it.data!!.map { studyResponse ->
                        studyResponse.toStudyItem()
                    })
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                    val errorResponse = (it as? HttpException)?.toErrorResponse()
                    if (errorResponse != null) {
                        Logger.d("${errorResponse.message}")
                    }
                })
        }
    }

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