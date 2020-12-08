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
import com.iron.espresso.model.response.notice.NoticeListResponse
import retrofit2.HttpException

class NoticeViewModel : BaseViewModel() {

    private val _noticeListItem = MutableLiveData<NoticeListResponse>()
    val noticeListItem: LiveData<NoticeListResponse>
        get() = _noticeListItem

    @SuppressLint("CheckResult")
    fun showNoticeList(studyId: Int) {

        ApiModule.provideNoticeApi()
            .getNoticeList(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({ response ->
                if (response.data != null) {
                    _noticeListItem.value = response.data
                }
                Logger.d("$response")
            }) {
                Logger.d("$it")
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            }
    }
}