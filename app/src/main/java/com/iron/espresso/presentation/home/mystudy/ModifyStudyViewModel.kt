package com.iron.espresso.presentation.home.mystudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.*
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.ModifyStudyRequest
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class ModifyStudyViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
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
        modifyStudyItem: ModifyStudyItem
    ): ValidationInputText {
        return when {
            modifyStudyItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            modifyStudyItem.introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            modifyStudyItem.progress.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            modifyStudyItem.studyTime.isEmpty() -> ValidationInputText.EMPTY_TIME
            (modifyStudyItem.localItem == null) -> ValidationInputText.EMPTY_PLACE
            else -> ValidationInputText.REGISTER_STUDY
        }
    }

    fun modifyStudy(studyId: Int, modifyStudyItem: ModifyStudyItem) {
        val message = emptyCheck(modifyStudyItem)
        if (message == ValidationInputText.REGISTER_STUDY && modifyStudyItem.localItem != null) {
            compositeDisposable += studyApi
                .modifyStudy(
                    bearerToken = AuthHolder.bearerToken,
                    studyId = studyId,
                    body = ModifyStudyRequest(
                        category = modifyStudyItem.category,
                        title = modifyStudyItem.title,
                        introduce = modifyStudyItem.introduce,
                        progress = modifyStudyItem.progress,
                        studyTime = modifyStudyItem.studyTime,
                        latitude = modifyStudyItem.localItem.lat,
                        longitude = modifyStudyItem.localItem.lng,
                        sido = modifyStudyItem.localItem.sido,
                        sigungu = modifyStudyItem.localItem.sigungu,
                        addressName = modifyStudyItem.localItem.addressName,
                        placeName = modifyStudyItem.localItem.placeName,
                        locationDetail = modifyStudyItem.localItem.locationDetail,
                        snsNotion = modifyStudyItem.snsNotion,
                        snsEverNote = modifyStudyItem.snsEverNote,
                        snsWeb = modifyStudyItem.snsWeb,
                        image = modifyStudyItem.image,
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