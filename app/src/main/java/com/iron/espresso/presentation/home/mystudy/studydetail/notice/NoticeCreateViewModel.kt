package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.api.RegisterNoticeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoticeCreateViewModel @Inject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    private var pinnedType: Boolean = false

    fun changePinned() {
        pinnedType = !pinnedType
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
                        pinned = pinnedType
                    )
                )
                .networkSchedulers()
                .subscribe({
                    _emptyCheckMessage.value = Event(message)
                    Logger.d("$it")
                }, {
                    val errorResponse = it.toErrorResponse()
                    if (errorResponse?.message != null) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                    Logger.d("$it")
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}