package com.iron.espresso.model.api

import com.google.gson.JsonObject
import com.iron.espresso.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("/v1/user/login")
    fun getUser(
        @Body body: JsonObject
    ): Call<UserResponse>


    @POST("/v1/user")
    fun registerUser(
        @Body body: JsonObject
    ): Call<String>

    @GET("/v1/check-nickname")
    fun checkDuplicateEmail(
        @Query("email") email: String
    ): Call<String>

    @GET("/v1/check-email")
    fun checkDuplicateNickname(
        @Query("nickname") nickname: String
    ): Call<String>

//    /v1/user/check-nickname/:nickname


}