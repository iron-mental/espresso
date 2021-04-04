package com.iron.espresso.presentation.home.mystudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import retrofit2.HttpException

class MyStudyViewModel @ViewModelInject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<MyStudyItem>>()
    val studyList: LiveData<List<MyStudyItem>>
        get() = _studyList

    private val _badgeCount = MutableLiveData<Int>()
    val badgeCount: LiveData<Int>
        get() = _badgeCount

    fun showMyStudyList() {
        compositeDisposable += studyRepository
            .getMyStudyList(
                userId = AuthHolder.id ?: -1
            )
            .map {
                it.toMyStudyListItem()
            }
            .networkSchedulers()
            .subscribe({
                _badgeCount.value = it.badge.alert
                _studyList.value = it.myStudyItem
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }
}