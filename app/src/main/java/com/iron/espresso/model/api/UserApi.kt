package com.iron.espresso.model.api

import com.google.gson.JsonObject
import com.iron.espresso.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/v1/user/login")
    fun getUser(
        @Body body: JsonObject
    ): Call<UserResponse>

}