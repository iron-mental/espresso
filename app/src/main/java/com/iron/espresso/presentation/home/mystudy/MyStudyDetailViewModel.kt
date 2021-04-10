package com.iron.espresso.presentation.home.mystudy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyDetailItem
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.domain.usecase.DeleteAllChat
import com.iron.espresso.domain.usecase.DeleteChat
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyStudyDetailViewModel @Inject constructor(
    private val studyRepository: StudyRepository,
    private val deleteChat: DeleteChat,
    private val deleteAllChat: DeleteAllChat
) : BaseViewModel() {

    private val _studyDetail = MutableLiveData<StudyDetailItem>()
    val studyDetail: LiveData<StudyDetailItem>
        get() = _studyDetail

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>>
        get() = _successEvent

    private val _failureEvent = MutableLiveData<Event<String>>()
    val failureEvent: LiveData<Event<String>>
        get() = _failureEvent

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
                Logger.d("$errorResponse")
                _failureEvent.value = Event(errorResponse?.message.orEmpty())

            })
    }

    fun leaveStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .leaveStudy(
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                delete(studyId)
                _toastMessage.value = Event(it.message.orEmpty())
                _successEvent.value = Event(Unit)
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
                _toastMessage.value = Event((it.message.orEmpty()))
            })
    }

    fun deleteStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .deleteStudy(
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                deleteAll(studyId)
                _toastMessage.value = Event(it.message.orEmpty())
                _successEvent.value = Event(Unit)
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }

    private fun delete(studyId: Int) {
        compositeDisposable += deleteChat(studyId)
            .networkSchedulers()
            .subscribe()
    }

    private fun deleteAll(studyId: Int) {
        compositeDisposable += deleteAllChat(studyId)
            .networkSchedulers()
            .subscribe()
    }
}