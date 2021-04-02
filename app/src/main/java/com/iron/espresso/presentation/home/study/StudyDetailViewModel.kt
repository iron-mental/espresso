package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.domain.usecase.RegisterApply
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class StudyDetailViewModel @ViewModelInject constructor(
    private val studyApi: StudyApi,
    private val registerApply: RegisterApply
) : AbsStudyDetailViewModel() {

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    private val _success = MutableLiveData<Event<Unit>>()
    val success: LiveData<Event<Unit>>
        get() = _success

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
                val errorResponse = it.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }

    fun sendApply(studyId: Int, message: String) {
        showLoading()
        val checkMessage = emptyCheck(message)
        if (checkMessage == ValidationInputText.SUCCESS) {
            compositeDisposable += registerApply(
                studyId = studyId,
                message = message
            )
                .networkSchedulers()
                .subscribe({ (success, message) ->
                    if (success) {
                        _success.value = Event(Unit)
                    }
                    _toastMessage.value = Event(message)
                    hideLoading()
                }, {
                    Logger.d("$it")
                    val errorResponse = it.toErrorResponse()
                    if (errorResponse?.message != null) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                    hideLoading()
                })

        } else {
            _emptyCheckMessage.value = Event(checkMessage)
        }
    }
}