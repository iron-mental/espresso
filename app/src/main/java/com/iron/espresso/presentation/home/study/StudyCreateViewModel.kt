package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class StudyCreateViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _localItem = MutableLiveData<LocalItem>()
    val localItem: LiveData<LocalItem>
        get() = _localItem

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    fun addItems(localItem: LocalItem?) {
        if (localItem != null) {
            _localItem.value = LocalItem(
                lat = localItem.lat,
                lng = localItem.lng,
                sido = localItem.sido,
                sigungu = localItem.sigungu,
                addressName = localItem.addressName,
                placeName = localItem.placeName,
                locationDetail = localItem.locationDetail
            )
        }
    }

    private fun emptyCheck(
        createStudyItem: CreateStudyItem
    ): ValidationInputText {
        return when {
            createStudyItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            createStudyItem.introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            createStudyItem.progress.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            createStudyItem.studyTime.isEmpty() -> ValidationInputText.EMPTY_TIME
            (createStudyItem.localItem == null) -> ValidationInputText.EMPTY_PLACE
            else -> ValidationInputText.REGISTER_STUDY
        }
    }

    fun createStudy(createStudyItem: CreateStudyItem) {
        val message = emptyCheck(createStudyItem)
        if (message == ValidationInputText.REGISTER_STUDY && createStudyItem.localItem != null) {
            compositeDisposable += studyApi
                .registerStudy(
                    bearerToken = AuthHolder.bearerToken,
                    body = RegisterStudyRequest(
                        category = createStudyItem.category,
                        title = createStudyItem.title,
                        introduce = createStudyItem.introduce,
                        progress = createStudyItem.progress,
                        studyTime = createStudyItem.studyTime,
                        latitude = createStudyItem.localItem.lat,
                        longitude = createStudyItem.localItem.lng,
                        sido = createStudyItem.localItem.sido,
                        sigungu = createStudyItem.localItem.sigungu,
                        addressName = createStudyItem.localItem.addressName,
                        placeName = createStudyItem.localItem.placeName,
                        locationDetail = createStudyItem.localItem.locationDetail,
                        snsNotion = createStudyItem.snsNotion,
                        snsEverNote = createStudyItem.snsEverNote,
                        snsWeb = createStudyItem.snsWeb,
                        image = createStudyItem.image,
                    ).toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    _emptyCheckMessage.value = Event(message)
                }, {
                    val errorResponse = (it as? HttpException)?.toErrorResponse()
                    if (errorResponse?.message != null) {
                        _toastMessage.value = Event("${errorResponse.message}")
                    } else {
                        _toastMessage.value = Event("Communication Error")
                    }
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}