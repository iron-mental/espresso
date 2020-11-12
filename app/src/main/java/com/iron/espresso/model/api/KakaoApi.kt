package com.iron.espresso.model.api

import com.iron.espresso.model.response.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {

    //키워드로 장소 검색
    @GET("v2/local/search/keyword.json")
    fun getPlacesByKeyword(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<PlaceResponse>
}