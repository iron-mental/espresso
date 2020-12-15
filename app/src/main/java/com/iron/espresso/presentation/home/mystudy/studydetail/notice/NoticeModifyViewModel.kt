package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.ModifyNoticeRequest
import com.iron.espresso.model.api.NoticeApi
import retrofit2.HttpException

class NoticeModifyViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _pinned = MutableLiveData<NoticeItemType>()
    val pinned: LiveData<NoticeItemType>
        get() = _pinned

    fun pinnedCheck(pinned: Boolean) {
        if (pinned) {
            _pinned.value = NoticeItemType.HEADER
        } else {
            _pinned.value = NoticeItemType.ITEM
        }
    }

    fun changePinned(pinned: Boolean) {
        if (pinned) {
            _pinned.value = NoticeItemType.ITEM
        } else {
            _pinned.value = NoticeItemType.HEADER
        }
    }

    @SuppressLint("CheckResult")
    fun modifyNotice(studyId: Int, noticeId: Int, noticeItem: NoticeItem) {
        noticeApi
            .modifyNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId,
                body = ModifyNoticeRequest(
                    title = noticeItem.title,
                    contents = noticeItem.contents,
                    pinned = noticeItem.pinned
                )
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    _toastMessage.value = Event("${errorResponse.message}")
                } else {
                    _toastMessage.value = Event("임시 공지사항 수정 성공")
                }

                Logger.d("$errorResponse")
            })
    }
}