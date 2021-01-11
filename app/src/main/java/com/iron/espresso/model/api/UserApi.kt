package com.iron.espresso.model.api

import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.AccessTokenResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.model.response.user.UserResponse
import com.iron.espresso.model.source.remote.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @POST("/v1/user/login")
    fun login(
        @Body body: LoginRequest
    ): Single<BaseResponse<UserAuthResponse>>

    @GET("/v1/user/{id}")
    fun getUser(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int
    ): Single<BaseResponse<UserResponse>>

    @POST("/v1/user")
    fun registerUser(
        @Body body: RegisterUserRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/check-email/{email}")
    fun checkDuplicateEmail(
        @Path("email") email: String
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/check-nickname/{nickname}")
    fun checkDuplicateNickname(
        @Query("nickname") nickname: String
    ): Single<BaseResponse<Nothing>>

    @Multipart
    @PUT("/v1/user/{id}")
    fun modifyUser(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Part body: List<MultipartBody.Part>
    ): Single<BaseResponse<Nothing>>

    @DELETE("/v1/user/{id}")
    fun deleteUser(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}/emailVerify")
    fun verifyEmail(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int
    ): Single<BaseResponse<Nothing>>

    @POST("/v1/firebase/reset-password/{email}")
    fun resetPassword(
        @Path("email") email: String,
    ): Single<BaseResponse<Nothing>>

    @POST("/v1/user/reissuance")
    fun reIssuanceAccessToken(
        @Header("Authorization") bearerToken: String,
        @Body refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>>


    @Multipart
    @PUT("/v1/user/{id}/image")
    fun modifyUserImage(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/info")
    fun modifyUserInfo(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: ModifyUserInfoRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/career")
    fun modifyUserCareer(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: ModifyUserCareerRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/sns")
    fun modifyUserSns(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: ModifyUserSnsRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/location")
    fun modifyUserLocation(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: ModifyUserLocationRequest
    ): Single<BaseResponse<Nothing>>
}