package com.iron.espresso.presentation.home.study.list

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

class StudyListViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<StudyItem>>()
    val studyList: LiveData<List<StudyItem>>
        get() = _studyList

    private val _scrollItem = MutableLiveData<List<StudyItem>>()
    val scrollItem: LiveData<List<StudyItem>>
        get() = _scrollItem

    private val allList = mutableListOf<StudyItem>()
    private var moreItemSize = -1
    private var totalSize = -1
    private var itemCount = -1
    private var loading = false


    private fun firstItemResult(studyList: List<StudyItem>): List<StudyItem> {
        val list: MutableList<StudyItem> = mutableListOf()
        moreItemSize = VISIBLE_ITEM_SIZE
        itemCount = 0
        loading = false
        allList.clear()
        allList.addAll(studyList)
        totalSize = studyList.size

        return if (totalSize <= VISIBLE_ITEM_SIZE) {
            studyList
        } else {
            loading = true
            for (i in 0 until VISIBLE_ITEM_SIZE) {
                itemCount++
                list.add(allList[i])
            }
            list
        }
    }

    private fun scrollMoreItem(): String {
        val list = mutableListOf<Int>()
        moreItemSize += VISIBLE_ITEM_SIZE
        return when {
            moreItemSize <= totalSize -> {
                for (i in itemCount until itemCount + VISIBLE_ITEM_SIZE) {
                    itemCount++
                    list.add(allList[i].id)
                }
                list.joinToString(",")
            }
            moreItemSize > totalSize -> {
                loading = false
                for (i in itemCount until allList.size) {
                    itemCount++
                    list.add(allList[i].id)
                }

                list.joinToString(",")
            }
            else -> {
                loading = false
                error("validation error")
            }
        }
    }

    fun getStudyList(category: String, sort: String) {
        compositeDisposable += studyApi
            .getStudyList(
                bearerToken = AuthHolder.bearerToken,
                category = category,
                sort = sort
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                if (it.data != null) {
                    val studyList =
                        firstItemResult(
                            it.data.map { studyResponse ->
                                studyResponse.toStudyItem()
                            }
                        )
                    _studyList.value = studyList
                }
            }, {
                Logger.d("$it")
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("${errorResponse.message}")
                }
            })
    }

    fun getStudyListPaging() {
        if (loading) {
            compositeDisposable += studyApi
                .getStudy(
                    bearerToken = AuthHolder.bearerToken,
                    studyIds = scrollMoreItem()
                )
                .networkSchedulers()
                .subscribe({
                    _scrollItem.value = it.data?.map { studyResponse ->
                        studyResponse.toStudyItem()
                    }
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

    companion object {
        private const val VISIBLE_ITEM_SIZE = 10
    }
}