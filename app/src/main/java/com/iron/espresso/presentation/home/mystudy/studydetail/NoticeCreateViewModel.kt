package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.api.RegisterNoticeRequest
import retrofit2.HttpException

class NoticeCreateViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {


    private fun emptyCheck(noticeItem: NoticeItem): Boolean {
        return when {
            noticeItem.title.isEmpty() -> false
            noticeItem.contents.isEmpty() -> false
            else -> true
        }
    }

    @SuppressLint("CheckResult")
    fun createNotice(studyId: Int, noticeItem: NoticeItem) {

        val message = emptyCheck(noticeItem)

        if (message) {
            noticeApi
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
                    _toastMessage.value = Event("임시 스터디 등록 성공")
                    Logger.d("$it")
                }, {
                    val errorResponse = (it as HttpException).toErrorResponse()
                    if (errorResponse.message != null) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                    Logger.d("$it")
                })
        } else {
            _toastMessage.value = Event("임시 스터 등록 실패")
        }
    }
}