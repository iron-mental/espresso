package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class StudyCreateViewModel @ViewModelInject constructor(
    private val studyApi: StudyApi
) :
    BaseViewModel() {

    private val _localItem = MutableLiveData<LocalItem>()
    val localItem: LiveData<LocalItem>
        get() = _localItem

    private val _snackBarText = MutableLiveData<Event<ValidationInputText>>()
    val snackBarMessage: LiveData<Event<ValidationInputText>>
        get() = _snackBarText

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
        studyItem: StudyItem
    ): ValidationInputText {
        return when {
            studyItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            studyItem.introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            studyItem.progress.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            studyItem.studyTime.isEmpty() -> ValidationInputText.EMPTY_TIME
            (studyItem.localItem == null) -> ValidationInputText.EMPTY_PLACE
            else -> ValidationInputText.REGISTER_STUDY
        }
    }

    @SuppressLint("CheckResult")
    fun createStudy(studyItem: StudyItem) {
        val message = emptyCheck(studyItem)
        if (message == ValidationInputText.REGISTER_STUDY && studyItem.localItem != null) {
            studyApi
                .registerStudy(
                    bearerToken = AuthHolder.bearerToken,
                    body = RegisterStudyRequest(
                        category = "android",
                        title = studyItem.title,
                        introduce = studyItem.introduce,
                        progress = studyItem.progress,
                        studyTime = studyItem.studyTime,
                        latitude = studyItem.localItem.lat,
                        longitude = studyItem.localItem.lng,
                        sido = studyItem.localItem.sido,
                        sigungu = studyItem.localItem.sigungu,
                        addressName = studyItem.localItem.addressName,
                        placeName = studyItem.localItem.placeName,
                        locationDetail = studyItem.localItem.locationDetail,
                        snsNotion = studyItem.snsNotion,
                        snsEverNote = studyItem.snsEverNote,
                        snsWeb = studyItem.snsWeb,
                        image = studyItem.image,
                    ).toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    _snackBarText.value = Event(message)
                }, {
                    val errorResponse = (it as? HttpException)?.toErrorResponse()
                    if (errorResponse?.message != null) {
                        _toastMessage.value = Event("${errorResponse.message}")
                    } else {
                        _toastMessage.value = Event("error")
                    }
                })
        } else {
            _snackBarText.value = Event(message)
        }
    }
}