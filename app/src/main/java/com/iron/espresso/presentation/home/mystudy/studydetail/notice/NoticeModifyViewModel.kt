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
import com.iron.espresso.model.api.ModifyNoticeRequest
import com.iron.espresso.model.api.NoticeApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NoticeModifyViewModel @Inject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    private var pinnedType: Boolean = false

    fun initPin(pinned: Boolean) {
        pinnedType = pinned
    }

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

    fun modifyNotice(studyId: Int, noticeId: Int, title: String, contents: String) {

        val message = emptyCheck(title, contents)

        if (message == ValidationInputText.REGISTER_NOTICE) {
            compositeDisposable += noticeApi
                .modifyNotice(
                    bearerToken = AuthHolder.bearerToken,
                    studyId = studyId,
                    noticeId = noticeId,
                    body = ModifyNoticeRequest(
                        title = title,
                        contents = contents,
                        pinned = pinnedType
                    )
                )
                .networkSchedulers()
                .subscribe({
                    Logger.d("$it")
                    _emptyCheckMessage.value = Event(ValidationInputText.MODIFY_NOTICE)
                }, {
                    val errorResponse = (it as? HttpException)?.toErrorResponse()
                    if (errorResponse != null) {
                        _toastMessage.value = Event("${errorResponse.message}")
                    }

                    Logger.d("$errorResponse")
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}