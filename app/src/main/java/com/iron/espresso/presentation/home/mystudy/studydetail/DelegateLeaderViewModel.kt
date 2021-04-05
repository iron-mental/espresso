package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DelegateLeaderViewModel @Inject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    fun delegateStudyLeader(studyId: Int, newLeader: Int) {
        compositeDisposable += studyRepository
            .delegateStudyLeader(
                studyId = studyId,
                newLeader = newLeader
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