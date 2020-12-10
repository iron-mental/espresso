package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterNoticeRequest

class NoticeCreateViewModel : BaseViewModel() {

    val noticeItem = MutableLiveData<NoticeItem>()

    @SuppressLint("CheckResult")
    fun createNotice(studyId: Int, noticeItem: NoticeItem) {
        ApiModule.provideNoticeApi()
            .registerNotice(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId,
                body = RegisterNoticeRequest(
                    title = noticeItem.title,
                    contents = noticeItem.contents,
                    pinned = noticeItem.pinned
                )
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }
}