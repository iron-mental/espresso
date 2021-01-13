package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.api.RegisterNoticeRequest
import retrofit2.HttpException

class NoticeCreateViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    private val _pinnedType = MutableLiveData<NoticeItemType>()
    val pinnedType: LiveData<NoticeItemType>
        get() = _pinnedType

    fun initPin() {
        _pinnedType.value = NoticeItemType.ITEM
    }

    fun changePinned() {
        _pinnedType.value = if (_pinnedType.value == NoticeItemType.HEADER) {
            NoticeItemType.ITEM
        } else {
            NoticeItemType.HEADER
        }
    }

    private fun emptyCheck(title: String, contents: String): ValidationInputText {
        return when {
            title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            contents.isEmpty() -> ValidationInputText.EMPTY_CONTENTS
            else -> ValidationInputText.REGISTER_NOTICE
        }
    }

    fun createNotice(studyId: Int, title: String, contents: String) {

        val message = emptyCheck(title, contents)

        if (message == ValidationInputText.REGISTER_NOTICE) {
            compositeDisposable += noticeApi
                .registerNotice(
                    bearerToken = AuthHolder.bearerToken,
                    studyId = studyId,
                    body = RegisterNoticeRequest(
                        title = title,
                        contents = contents,
                        pinned = _pinnedType.value == NoticeItemType.HEADER
                    )
                )
                .networkSchedulers()
                .subscribe({
                    _emptyCheckMessage.value = Event(message)
                    Logger.d("$it")
                }, {
                    val errorResponse = (it as HttpException).toErrorResponse()
                    if (errorResponse.message != null) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                    Logger.d("$it")
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}