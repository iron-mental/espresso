package com.iron.espresso.model.api

import com.iron.espresso.model.response.LocalResponse
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

    //좌표로 주소 변환하기
    @GET("v2/local/geo/coord2address.json")
    fun convertAddressToCoord(
        @Header("Authorization") authorization: String,
        @Query("x") lng: Double,
        @Query("y") lat: Double
    ): Call<LocalResponse>
}