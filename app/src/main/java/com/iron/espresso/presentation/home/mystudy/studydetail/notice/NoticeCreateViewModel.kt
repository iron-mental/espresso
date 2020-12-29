package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.api.RegisterNoticeRequest
import retrofit2.HttpException

class NoticeCreateViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _snackBarText = MutableLiveData<Event<ValidationInputText>>()
    val snackBarMessage: LiveData<Event<ValidationInputText>>
        get() = _snackBarText

    private fun emptyCheck(noticeItem: NoticeItem): ValidationInputText {
        return when {
            noticeItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            noticeItem.contents.isEmpty() -> ValidationInputText.EMPTY_CONTENTS
            else -> ValidationInputText.REGISTER_NOTICE
        }
    }

    @SuppressLint("CheckResult")
    fun createNotice(studyId: Int, noticeItem: NoticeItem) {

        val message = emptyCheck(noticeItem)

        if (message == ValidationInputText.REGISTER_NOTICE) {
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
                    _snackBarText.value = Event(message)
                    Logger.d("$it")
                }, {
                    val errorResponse = (it as HttpException).toErrorResponse()
                    if (errorResponse.message != null) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                    Logger.d("$it")
                })
        } else {
            _snackBarText.value = Event(message)
        }
    }
}