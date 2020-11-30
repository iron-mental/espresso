package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import retrofit2.HttpException
import java.io.File

class StudyCreateViewModel @ViewModelInject constructor(
    private val studyApi: StudyApi,
    private val context: Application
) :
    BaseViewModel() {

    val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"

    val registerStudyRequest = MutableLiveData<RegisterStudyRequest>().apply {
        value = RegisterStudyRequest(
            category = "",
            title = "",
            introduce = "",
            progress = "",
            studyTime = "",
            latitude = 0.0,
            longitude = 0.0,
            sido = "",
            sigungu = "",
            addressName = "",
            placeName = "",
            locationDetail = "",
            snsNotion = "",
            snsEverNote = "",
            snsWeb = "",
            image = File("/storage/0000-0000/DCIM/Camera/20201120_190322.jpg")
        )
    }

    fun addItems(localItem: LocalItem) {
        registerStudyRequest.value = RegisterStudyRequest(
            category = "",
            title = "",
            introduce = "",
            progress = "",
            studyTime = "",
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
            image = File("/storage/0000-0000/DCIM/Camera/20201120_190322.jpg")
        )
    }

    private fun emptyCheck(registerStudyRequest: RegisterStudyRequest): String {
        return when {
            registerStudyRequest.title.isEmpty() -> context.getString(R.string.empty_title)
            registerStudyRequest.introduce.isEmpty() -> context.getString(R.string.empty_introduce)
            registerStudyRequest.progress.isEmpty() -> context.getString(R.string.empty_progress)
            registerStudyRequest.addressName.isEmpty() -> context.getString(R.string.empty_place)
            registerStudyRequest.studyTime.isEmpty() -> context.getString(R.string.empty_time)
            else -> context.getString(R.string.success_register)
        }
    }

    @SuppressLint("CheckResult")
    fun createStudy(registerStudyRequest: RegisterStudyRequest, callback: (item: String) -> Unit) {

        val message = emptyCheck(registerStudyRequest)

        if (message == context.getString(R.string.success_register)) {
            studyApi
                .registerStudy(
                    bearerToken = "Bearer $token",
                    body = registerStudyRequest.toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    callback(message)
                }, {
                    val error = it as? HttpException
                    val errorBody = error?.response()?.errorBody()?.string()
                    Logger.d("${error?.code()}")
                    Logger.d("$errorBody")
                    val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                    Logger.d("$errorResponse")
                    if (errorResponse.message != null) {
                        callback("${errorResponse.message}")
                    } else {
                        callback("error")
                    }
                })
        } else {
            callback(message)
        }
    }
}