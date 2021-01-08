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
import com.iron.espresso.model.api.ModifyNoticeRequest
import com.iron.espresso.model.api.NoticeApi
import retrofit2.HttpException

class NoticeModifyViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _snackBarText = MutableLiveData<Event<ValidationInputText>>()
    val snackBarMessage: LiveData<Event<ValidationInputText>>
        get() = _snackBarText

    private val _pinnedType = MutableLiveData<NoticeItemType>()
    val pinnedType: LiveData<NoticeItemType>
        get() = _pinnedType

    fun initPin(pinned: Boolean) {
        _pinnedType.value = if (pinned) {
            NoticeItemType.HEADER
        } else {
            NoticeItemType.ITEM
        }
    }

    fun changePinned() {
        _pinnedType.value = if (_pinnedType.value == NoticeItemType.HEADER) {
            NoticeItemType.ITEM
        } else {
            NoticeItemType.HEADER
        }
    }

    fun modifyNotice(studyId: Int, noticeId: Int, title: String, contents: String) {
        compositeDisposable += noticeApi
            .modifyNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId,
                body = ModifyNoticeRequest(
                    title = title,
                    contents = contents,
                    pinned = _pinnedType.value == NoticeItemType.HEADER
                )
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                _snackBarText.value = Event(ValidationInputText.MODIFY_NOTICE)
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    _toastMessage.value = Event("${errorResponse.message}")
                }

                Logger.d("$errorResponse")
            })
    }
}