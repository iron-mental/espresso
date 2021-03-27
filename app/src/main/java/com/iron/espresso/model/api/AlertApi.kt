package com.iron.espresso.model.api

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.alert.AlertResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AlertApi {

    @GET("/v1/user/{id}/alert")
    fun getAlertList(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId()
    ): Single<BaseResponse<List<AlertResponse>>>

    @GET("/v1/user/{id}/alert/{alert_id}")
    fun readAlert(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Path("alert_id") alertId: Int
    ): Single<BaseResponse<Nothing>>
}