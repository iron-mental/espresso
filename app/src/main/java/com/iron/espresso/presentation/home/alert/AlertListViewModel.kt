package com.iron.espresso.presentation.home.alert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.GetAlertList
import com.iron.espresso.domain.usecase.ReadAlert
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertListViewModel @Inject constructor(
    private val getAlertList: GetAlertList,
    private val readAlert: ReadAlert
) : BaseViewModel() {

    private val _alertList = MutableLiveData<List<AlertItem>>()
    val alertList: LiveData<List<AlertItem>>
        get() = _alertList

    fun getList() {
        compositeDisposable += getAlertList()
            .map { list ->
                list.map {
                    AlertItem.of(it)
                }
            }
            .networkSchedulers()
            .subscribe({ list ->
                _alertList.value = list
            }, { throwable ->
                throwable.toErrorResponse()?.let {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
                Logger.d("${throwable.printStackTrace()}")
            })

    }

    fun read(alertId: Int) {
        compositeDisposable += readAlert(alertId)
            .networkSchedulers()
            .subscribe({ (isSuccess, message) ->
                _toastMessage.value = Event(message)
            }, { throwable ->

                throwable.toErrorResponse()?.let {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
                Logger.d("$throwable")
            })
    }

}