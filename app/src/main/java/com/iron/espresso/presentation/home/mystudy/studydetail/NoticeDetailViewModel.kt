package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
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

    val notice = MutableLiveData<NoticeDetailResponse>()

    fun showNotice(studyId: Int, noticeId: Int) {
        getNotice(studyId, noticeId) { data ->
            notice.value = data
        }
    }

    @SuppressLint("CheckResult")
    private fun getNotice(
        studyId: Int,
        noticeId: Int,
        callback: (data: NoticeDetailResponse) -> Unit
    ) {
        ApiModule.provideNoticeApi()
            .getNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                noticeId = noticeId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    callback(it.data)
                }
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }
}