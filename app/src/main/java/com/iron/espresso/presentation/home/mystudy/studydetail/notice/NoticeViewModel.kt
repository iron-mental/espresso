package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.NoticeApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(private val noticeApi: NoticeApi) :
    BaseViewModel() {

    private val _noticeListItem = MutableLiveData<List<NoticeItem>>()
    val noticeListItem: LiveData<List<NoticeItem>>
        get() = _noticeListItem

    private val _scrollItem = MutableLiveData<List<NoticeItem>>()
    val scrollItem: LiveData<List<NoticeItem>>
        get() = _scrollItem

    private val allList = mutableListOf<NoticeItem>()
    private var moreItemSize = -1
    private var totalSize = -1
    private var itemCount = -1
    private var loading = false

    private fun firstItemResult(noticeList: List<NoticeItem>): List<NoticeItem> {
        val list: MutableList<NoticeItem> = mutableListOf()
        moreItemSize = VISIBLE_ITEM_SIZE
        itemCount = 0
        loading = false
        allList.clear()
        allList.addAll(noticeList)
        totalSize = noticeList.size

        return if (totalSize <= VISIBLE_ITEM_SIZE) {
            noticeList
        } else {
            loading = true
            for (i in 0 until VISIBLE_ITEM_SIZE) {
                itemCount++
                list.add(allList[i])
            }
            list
        }
    }

    private fun scrollMoreItem(): String {
        val list = mutableListOf<Int>()
        moreItemSize += VISIBLE_ITEM_SIZE
        return when {
            moreItemSize <= totalSize -> {
                for (i in itemCount until itemCount + VISIBLE_ITEM_SIZE) {
                    itemCount++
                    list.add(allList[i].id)
                }
                list.joinToString(",")
            }
            moreItemSize > totalSize -> {
                loading = false
                for (i in itemCount until allList.size) {
                    itemCount++
                    list.add(allList[i].id)
                }

                list.joinToString(",")
            }
            else -> {
                loading = false
                error("validation error")
            }
        }
    }

    fun showNoticeList(studyId: Int) {
        compositeDisposable += noticeApi
            .getNoticeList(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({ response ->
                if (response.data != null) {
                    _noticeListItem.value = firstItemResult(
                        response.data.map {
                            it.toNoticeItem()
                        }
                    )
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

    fun showNoticeListPaging() {
        if (loading) {
            compositeDisposable += noticeApi
                .getNoticeList(
                    bearerToken = AuthHolder.bearerToken,
                    noticeIds = scrollMoreItem()
                )
                .networkSchedulers()
                .subscribe({
                    _scrollItem.value = it.data?.map { noticeResponse ->
                        noticeResponse.toNoticeItem()
                    }
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                })
        }
    }

    companion object {
        private const val VISIBLE_ITEM_SIZE = 10
    }
}