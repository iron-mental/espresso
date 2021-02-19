package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
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
            else -> ValidationInputText.REGISTER_STUDY
        }
    }

    private fun duplicateItemCheck(title: String, modifyTitle: String): String {
        return if (title == modifyTitle) {
            ""
        } else {
            modifyTitle
        }
    }

    fun modifyStudy(studyId: Int, title: String, modifyStudyItem: ModifyStudyItem) {
        val message = emptyCheck(modifyStudyItem)
        if (message == ValidationInputText.REGISTER_STUDY) {
            compositeDisposable += studyApi
                .modifyStudy(
                    bearerToken = AuthHolder.bearerToken,
                    studyId = studyId,
                    body = ModifyStudyRequest(
                        category = modifyStudyItem.category,
                        title = duplicateItemCheck(title, modifyStudyItem.title),
                        introduce = modifyStudyItem.introduce,
                        progress = modifyStudyItem.progress,
                        studyTime = modifyStudyItem.studyTime,
                        latitude = modifyStudyItem.latitude,
                        longitude = modifyStudyItem.longitude,
                        sido = modifyStudyItem.sido,
                        sigungu = modifyStudyItem.sigungu,
                        addressName = modifyStudyItem.addressName,
                        placeName = modifyStudyItem.placeName,
                        locationDetail = modifyStudyItem.locationDetail,
                        snsNotion = modifyStudyItem.snsNotion,
                        snsEverNote = modifyStudyItem.snsEverNote,
                        snsWeb = modifyStudyItem.snsWeb,
                        image = modifyStudyItem.image,
                    ).toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    _emptyCheckMessage.value = Event(message)
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
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