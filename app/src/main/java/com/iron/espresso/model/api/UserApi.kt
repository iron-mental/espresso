package com.iron.espresso.model.api

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.address.AddressResponse
import com.iron.espresso.model.response.user.*
import com.iron.espresso.model.source.remote.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @POST("/v1/user/login")
    fun login(
        @Body body: LoginRequest
    ): Single<BaseResponse<UserAuthResponse>>

    @POST("/v1/user/logout")
    fun logout(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Body body: LogoutRequest = LogoutRequest(AuthHolder.requireId())
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}")
    fun getUser(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int
    ): Single<BaseResponse<UserResponse>>

    @POST("/v1/user")
    fun registerUser(
        @Body body: RegisterUserRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/check-email/{email}")
    fun checkDuplicateEmail(
        @Path("email") email: String
    ): Single<BaseResponse<CheckEmailResponse>>

    @GET("/v1/user/check-nickname/{nickname}")
    fun checkDuplicateNickname(
        @Path("nickname") nickname: String
    ): Single<BaseResponse<Nothing>>

    @Multipart
    @PUT("/v1/user/{id}")
    fun modifyUser(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int,
        @Part body: List<MultipartBody.Part>
    ): Single<BaseResponse<Nothing>>

    @HTTP(method = "DELETE", path = "/v1/user/{id}", hasBody = true)
    fun deleteUser(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: DeleteUserRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}/emailVerify")
    fun verifyEmail(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId()
    ): Single<BaseResponse<Nothing>>

    @POST("/v1/firebase/reset-password/{email}")
    fun resetPassword(
        @Path("email") email: String,
    ): Single<BaseResponse<Nothing>>

    @POST("/v1/user/reissuance")
    fun reIssuanceAccessToken(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Body refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>>

    @GET("/check-version")
    fun getVersionInfo(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Query("version") version: String,
        @Query("device") device: String = "android"
    ): Single<BaseResponse<VersionResponse>>


    @Multipart
    @PUT("/v1/user/{id}/image")
    fun modifyUserImage(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Part image: MultipartBody.Part?
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/info")
    fun modifyUserInfo(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: ModifyUserInfoRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/career")
    fun modifyUserCareer(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: ModifyUserCareerRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/sns")
    fun modifyUserSns(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: ModifyUserSnsRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/email")
    fun modifyUserEmail(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: ModifyUserEmailRequest
    ): Single<BaseResponse<Nothing>>

    @PUT("/v1/user/{id}/location")
    fun modifyUserLocation(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId(),
        @Body body: ModifyUserLocationRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/address")
    fun getAddressList(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken
    ): Single<BaseResponse<List<AddressResponse>>>

    @PUT("/v1/user/{id}/push_token")
    fun refreshPushToken(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") id: Int = AuthHolder.requireId()
    ): Completable
}