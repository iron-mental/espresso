package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.notice.NoticeDetailResponse
import retrofit2.HttpException

class NoticeDetailViewModel : BaseViewModel() {

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"

    val notice = MutableLiveData<NoticeDetailResponse>()

    fun showNotice(studyId: Int, noticeId: Int) {
        getNotice(studyId, noticeId) { data ->
            notice.value = data
        }
    }

    @SuppressLint("CheckResult")
    private fun getNotice(
        studyId: Int,
        noticeId: Int,
        callback: (data: NoticeDetailResponse) -> Unit
    ) {
        ApiModule.provideNoticeApi()
            .getNotice(
                bearerToken = "Bearer $token",
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    callback(it.data)
                }
            }, {
                val error = it as? HttpException
                val errorBody = error?.response()?.errorBody()?.string()
                Logger.d("${error?.code()}")
                Logger.d("$errorBody")
                val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                Logger.d("$errorResponse")
            })
    }

    @SuppressLint("CheckResult")
    fun deleteNotice(studyId: Int, noticeId: Int, callback: (data: String) -> Unit) {
        ApiModule.provideNoticeApi()
            .deleteNotice(
                bearerToken = "Bearer $token",
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.message != null) {
                    callback(it.message)
                }
            }, {
                Logger.d("$it")
            })
    }
}