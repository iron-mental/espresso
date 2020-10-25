package com.iron.espresso.model.api

import com.google.gson.JsonObject
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.UserResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("/v1/user/login")
    fun getUser(
        @Body body: JsonObject
    ): Single<UserResponse>

    @POST("/v1/user")
    fun registerUser(
        @Body body: JsonObject
    ): Single<MessageResponse>

    @GET("/v1/check-nickname")
    fun checkDuplicateEmail(
        @Query("email") email: String
    ): Single<MessageResponse>

    @GET("/v1/check-email")
    fun checkDuplicateNickname(
        @Query("nickname") nickname: String
    ): Single<MessageResponse>

}