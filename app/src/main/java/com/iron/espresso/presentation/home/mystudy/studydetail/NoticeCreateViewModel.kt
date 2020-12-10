package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.RegisterNoticeRequest
import retrofit2.HttpException

class NoticeCreateViewModel : BaseViewModel() {

    @SuppressLint("CheckResult")
    fun createNotice(studyId: Int, noticeItem: NoticeItem) {
        ApiModule.provideNoticeApi()
            .registerNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                body = RegisterNoticeRequest(
                    title = noticeItem.title,
                    contents = noticeItem.contents,
                    pinned = noticeItem.pinned
                )
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val errorResponse = (it as HttpException).toErrorResponse()
                if (errorResponse.message != null) {
                    _toastMessage.value = Event(errorResponse.message)
                }
                Logger.d("$it")
            })
    }
}