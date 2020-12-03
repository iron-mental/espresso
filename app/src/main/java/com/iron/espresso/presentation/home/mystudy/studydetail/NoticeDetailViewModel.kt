package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.response.BaseResponse
import retrofit2.HttpException

class NoticeDetailViewModel : BaseViewModel() {

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"

    @SuppressLint("CheckResult")
    fun getNotice(studyId: Int, noticeId: Int) {
        ApiModule.provideNoticeApi()
            .getNotice(
                bearerToken = "Bearer $token",
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val error = it as? HttpException
                val errorBody = error?.response()?.errorBody()?.string()
                Logger.d("${error?.code()}")
                Logger.d("$errorBody")
                val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                Logger.d("$errorResponse")
            })
    }
}