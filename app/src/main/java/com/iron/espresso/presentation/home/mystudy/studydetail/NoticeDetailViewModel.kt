package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.response.notice.NoticeDetailResponse
import retrofit2.HttpException

class NoticeDetailViewModel @ViewModelInject constructor(private val noticeApi: NoticeApi) : BaseViewModel() {

    private val _notice = MutableLiveData<NoticeDetailResponse>()
    val notice: LiveData<NoticeDetailResponse>
        get() = _notice

    @SuppressLint("CheckResult")
    fun showNotice(studyId: Int, noticeId: Int) {
        noticeApi
            .getNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _notice.value = it.data
                }
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }
}