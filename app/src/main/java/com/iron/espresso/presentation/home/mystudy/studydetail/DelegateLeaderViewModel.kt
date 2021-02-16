package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.DelegateRequest
import retrofit2.HttpException

class DelegateLeaderViewModel : BaseViewModel() {

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    fun delegateStudyLeader(studyId: Int, newLeader: Int) {
        compositeDisposable += ApiModule.provideStudyApi()
            .delegateStudyLeader(
                studyId = studyId,
                body = DelegateRequest(
                    newLeader = newLeader
                )
            )
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    _successEvent.value = Event(true)
                }
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse?.message != null) {
                    _toastMessage.value = Event(errorResponse.message)
                    Logger.d("$errorResponse")
                }
            })
    }
}