package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeDetailItem
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import retrofit2.HttpException

class NoticeDetailViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _notice = MutableLiveData<NoticeDetailItem>()
    val notice: LiveData<NoticeDetailItem>
        get() = _notice

    fun showNotice(studyId: Int, noticeId: Int) {
        compositeDisposable += noticeApi
            .getNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _notice.value = it.data.toNoticeDetailItem()
                }
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }

    fun deleteNotice(studyId: Int, noticeId: Int) {
        compositeDisposable += noticeApi
            .deleteNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.message != null) {
                    _toastMessage.value = Event(it.message)
                }
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse?.message != null) {
                    _toastMessage.value = Event(errorResponse.message)
                }
            })
    }
}