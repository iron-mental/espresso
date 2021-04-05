package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.AlertApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.alert.AlertResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface AlertRemoteDataSource {
    fun getAlertList(): Single<BaseResponse<List<AlertResponse>>>

    fun readAlert(alertId: Int): Single<BaseResponse<Nothing>>
}

class AlertRemoteDataSourceImpl @Inject constructor(private val alertApi: AlertApi) : AlertRemoteDataSource {

    override fun getAlertList(): Single<BaseResponse<List<AlertResponse>>> {
        return alertApi.getAlertList()
    }

    override fun readAlert(alertId: Int): Single<BaseResponse<Nothing>> {
        return alertApi.readAlert(alertId = alertId)
    }
}