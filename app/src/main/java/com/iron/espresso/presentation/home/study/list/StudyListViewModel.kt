package com.iron.espresso.presentation.home.study.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class StudyListViewModel @Inject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<StudyItem>>()
    val studyList: LiveData<List<StudyItem>>
        get() = _studyList

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

    fun getStudyList(category: String, sort: String) {
        showLoading()
        compositeDisposable += studyRepository
            .getStudyList(
                category = category,
                sort = sort
            )
            .map {
                it.map { studyResponse ->
                    studyResponse.toStudyItem()
                }
            }
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                _studyList.value = firstItemResult(it)
                hideLoading()
            }, {
                Logger.d("$it")
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("${errorResponse.message}")
                }
                hideLoading()
            })
    }

    fun getStudyListPaging(option: String, itemCount: Int) {
        if (isPaging) {
            compositeDisposable += studyRepository
                .getStudyPagingList(
                    studyIds = scrollMoreItem(itemCount),
                    option = option
                )
                .map {
                    it.map { studyResponse ->
                        studyResponse.toStudyItem()
                    }
                }
                .networkSchedulers()
                .subscribe({
                    _studyList.value = _studyList.value?.plus(it)
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