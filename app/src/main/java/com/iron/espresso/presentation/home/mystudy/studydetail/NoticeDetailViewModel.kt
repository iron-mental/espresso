package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.notice.NoticeDetailResponse
import retrofit2.HttpException

class NoticeDetailViewModel : BaseViewModel() {

    private val _notice = MutableLiveData<NoticeDetailResponse>()
    val notice: LiveData<NoticeDetailResponse>
        get() = _notice

    @SuppressLint("CheckResult")
    fun showNotice(studyId: Int, noticeId: Int) {
        ApiModule.provideNoticeApi()
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