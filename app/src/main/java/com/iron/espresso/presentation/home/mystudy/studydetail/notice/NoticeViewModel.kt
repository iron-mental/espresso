package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.NoticeApi
import com.iron.espresso.model.response.notice.NoticeListResponse

class NoticeViewModel : BaseViewModel() {

    val noticeListItem = MutableLiveData<NoticeListResponse>()

    fun showNoticeList(studyId: Int) {
        getNoticeList(studyId) { data ->
            noticeListItem.value = data
        }
    }

    @SuppressLint("CheckResult")
    private fun getNoticeList(studyId: Int, callback: (data: NoticeListResponse?) -> Unit) {

        ApiModule.provideNoticeApi()
            .getNoticeList(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({ response ->
                if (response.data != null) {
                    callback(response.data)
                }
            }) {
                callback(null)
            }
    }
}