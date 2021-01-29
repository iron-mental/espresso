package com.iron.espresso.presentation.home.study.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class StudyListViewModel @ViewModelInject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<StudyItem>>()
    val studyList: LiveData<List<StudyItem>>
        get() = _studyList

    private val _scrollItem = MutableLiveData<List<StudyItem>>()
    val scrollItem: LiveData<List<StudyItem>>
        get() = _scrollItem

    private val allList = mutableListOf<StudyItem>()
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
        return list
    }

    private fun scrollMoreItem(startSize: Int): String {
        val scrollIdList = mutableListOf<Int>()
        val pagingSize = studyList.value?.size ?: 0
        val endSize = startSize + pagingSize
        return when {
            endSize <= allList.size -> {
                for (i in startSize until endSize) {
                    scrollIdList.add(allList[i].id)
                }
                scrollIdList.joinToString(",")

            }
            endSize > allList.size -> {
                isPaging = false
                for (i in startSize until allList.size) {
                    scrollIdList.add(allList[i].id)
                }
                scrollIdList.joinToString(",")
            }
            else -> {
                isPaging = false
                error("validation error")
            }
        }
    }

    fun getStudyList(category: String, sort: String) {
        compositeDisposable += ApiModule.provideStudyApi()
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

    fun getStudyListPaging(sort: String, itemCount: Int) {
        if (isPaging) {
            compositeDisposable += studyRepository
                .getStudyPagingList(
                    sort = sort,
                    studyIds = scrollMoreItem(itemCount)
                )
                .map {
                    it.map { studyResponse ->
                        studyResponse.toStudyItem()
                    }
                }
                .delay(1000, TimeUnit.MILLISECONDS)
                .networkSchedulers()
                .subscribe({
                    _scrollItem.value = it
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
}