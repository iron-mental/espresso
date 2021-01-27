package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyDetailItem
import com.iron.espresso.domain.repo.ApplyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.RegisterStudyApplyRequest
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class StudyDetailViewModel @ViewModelInject constructor(
    private val studyApi: StudyApi,
    private val applyRepository: ApplyRepository
) :
    BaseViewModel() {

    private val _studyDetail = MutableLiveData<StudyDetailItem>()
    val studyDetail: LiveData<StudyDetailItem>
        get() = _studyDetail

    private fun emptyCheck(message: String): ValidationInputText {
        return if (message.isEmpty()) {
            ValidationInputText.EMPTY_CONTENTS
        } else {
            ValidationInputText.SUCCESS
        }
    }

    fun getStudy(studyId: Int) {
        compositeDisposable += studyApi
            .getStudyDetail(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyDetail.value = it.data.toStudyDetailItem()
                }
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }

    fun registerApply(studyId: Int, message: String) {
        if (emptyCheck(message) == ValidationInputText.SUCCESS) {
            compositeDisposable += applyRepository
                .registerApply(
                    studyId = studyId,
                    request = RegisterStudyApplyRequest(
                        message = message
                    )
                )
                .networkSchedulers()
                .subscribe({
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                })
        }
    }

}