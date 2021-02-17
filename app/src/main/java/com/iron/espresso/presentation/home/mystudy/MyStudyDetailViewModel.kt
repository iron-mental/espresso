package com.iron.espresso.presentation.home.mystudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyDetailItem
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import retrofit2.HttpException

class MyStudyDetailViewModel @ViewModelInject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _studyDetail = MutableLiveData<StudyDetailItem>()
    val studyDetail: LiveData<StudyDetailItem>
        get() = _studyDetail

    fun getStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .getStudyDetail(
                studyId = studyId
            )
            .map {
                it.toStudyDetailItem()
            }
            .networkSchedulers()
            .subscribe({
                _studyDetail.value = it
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }

    fun leaveStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .leaveStudy(
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                _toastMessage.value = Event(it.message.orEmpty())
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }

    fun deleteStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .deleteStudy(
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                _toastMessage.value = Event(it.message.orEmpty())
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }
}