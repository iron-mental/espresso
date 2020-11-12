package com.iron.espresso.model.api

import com.iron.espresso.presentation.home.study.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/local/search/keyword.json")
    fun getTest(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<PlaceResponse>
}