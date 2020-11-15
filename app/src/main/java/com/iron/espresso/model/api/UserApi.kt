package com.iron.espresso.model.api

import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.UserResponse
import com.iron.espresso.model.source.remote.LoginRequest
import com.iron.espresso.model.source.remote.RegisterUserRequest
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApi {

    @POST("/v1/user/login")
    fun login(
        @Body body: LoginRequest
    ): Single<LoginUser>

    @GET("/v1/user/{id}}")
    fun getUser(
        @Path(value = "id") id: Int
    ): Single<UserResponse>

    @POST("/v1/user")
    fun registerUser(
        @Body body: RegisterUserRequest
    ): Single<MessageResponse>

    @GET("/v1/check-nickname")
    fun checkDuplicateEmail(
        @Query("email") email: String
    ): Single<MessageResponse>

    @GET("/v1/check-email")
    fun checkDuplicateNickname(
        @Query("nickname") nickname: String
    ): Single<MessageResponse>

    @Multipart
    @PATCH("/v1/user/{id}")
    fun modifyUser(
        @Body body: RequestBody
    ): Single<UserResponse>

}