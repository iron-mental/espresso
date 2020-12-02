package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.response.notice.NoticeListResponse

class NoticeViewModel : BaseViewModel() {

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"

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
                bearerToken = "Bearer $token",
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