package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.annotation.SuppressLint
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.ModifyNoticeRequest

class NoticeModifyViewModel : BaseViewModel() {

    @SuppressLint("CheckResult")
    fun modifyNotice(studyId: Int, noticeId: Int, noticeItem: NoticeItem) {
        ApiModule.provideNoticeApi()
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
                Logger.d("$it")
            })
    }
}