package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.iron.espresso.AuthHolder
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
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
        title: String,
        introduce: String,
        proceed: String,
        time: String,
        localItem: LocalItem?
    ): ValidationInputText {
        return when {
            title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            proceed.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            time.isEmpty() -> ValidationInputText.EMPTY_TIME
            (localItem == null) -> ValidationInputText.EMPTY_PLACE
            else -> ValidationInputText.SUCCESS
        }
    }

    @SuppressLint("CheckResult")
    fun createStudy(
        title: String,
        introduce: String,
        proceed: String,
        time: String,
        localItem: LocalItem?
    ) {
        val message = emptyCheck(title, introduce, proceed, time, localItem)
        if (message == ValidationInputText.SUCCESS && localItem != null) {
            studyApi
                .registerStudy(
                    bearerToken = AuthHolder.bearerToken,
                    body = RegisterStudyRequest(
                        category = "android",
                        title = title,
                        introduce = introduce,
                        progress = proceed,
                        studyTime = time,
                        latitude = localItem.lat,
                        longitude = localItem.lng,
                        sido = localItem.sido,
                        sigungu = localItem.sigungu,
                        addressName = localItem.addressName,
                        placeName = localItem.placeName,
                        locationDetail = localItem.locationDetail,
                        snsNotion = "",
                        snsEverNote = "",
                        snsWeb = "",
                        image = null,
                    ).toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    _snackBarText.value = Event(message)
                }, {
                    val error = it as? HttpException
                    val errorBody = error?.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                    if (errorResponse.message != null) {
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