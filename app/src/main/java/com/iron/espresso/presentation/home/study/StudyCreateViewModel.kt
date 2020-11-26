package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterStudyRequest

class StudyCreateViewModel : BaseViewModel() {

    val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"

    val registerStudyRequest = MutableLiveData<RegisterStudyRequest>()

    fun addItems(items: RegisterStudyRequest, localItem: LocalItem) {
        registerStudyRequest.value = RegisterStudyRequest(
            category = items.category,
            title = items.title,
            introduce = items.introduce,
            progress = items.progress,
            studyTime = items.studyTime,
            latitude = localItem.lat,
            longitude = localItem.lng,
            sido = localItem.sido,
            sigungu = localItem.sigungu,
            addressName = localItem.addressName,
            placeName = localItem.placeName,
            locationDetail = localItem.locationDetail,
            snsNotion = items.snsNotion,
            snsEverNote = items.snsEverNote,
            snsWeb = items.snsWeb,
            image = items.image
        )
    }

    private fun emptyCheck(registerStudyRequest: RegisterStudyRequest): String {
        return when {
            registerStudyRequest.title.isEmpty() -> "제목을 작성하세요"
            registerStudyRequest.introduce.isEmpty() -> "소개를 작성하세요"
            registerStudyRequest.progress.isEmpty() -> "진행을 작성하세요"
            registerStudyRequest.addressName.isEmpty() -> "장소를 선택하세요"
            registerStudyRequest.studyTime.isEmpty() -> "시간을 작성하세요"
            else -> "스터디가 등록되었습니다"
        }
    }

    @SuppressLint("CheckResult")
    fun createStudy(registerStudyRequest: RegisterStudyRequest): String {

        if (emptyCheck(registerStudyRequest) == "스터디가 등록되었습니다") {
            ApiModule.provideStudyApi()
                .registerStudy(
                    bearerToken = "Bearer $token",
                    body = registerStudyRequest.toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                })
        }
        return emptyCheck(registerStudyRequest)
    }
}