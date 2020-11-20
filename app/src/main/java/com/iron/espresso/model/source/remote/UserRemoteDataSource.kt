package com.iron.espresso.model.source.remote

import com.google.gson.annotations.SerializedName
import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.AccessTokenResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.model.response.user.UserResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) :
    UserRemoteDataSource {

    override fun login(email: String, password: String): Single<BaseResponse<UserAuthResponse>> =
        userApi.login(LoginRequest(email, password))

    override fun getUser(bearerToken: String, id: Int): Single<BaseResponse<UserResponse>> =
        userApi.getUser(bearerToken, id)

    override fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>> =
        userApi.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>> =
        userApi.checkDuplicateNickname(nickname)

    override fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>> =
        userApi.registerUser(RegisterUserRequest(email, password, nickname))

    override fun modifyUser(
        bearerToken: String,
        id: Int,
        request: ModifyUserRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUser(bearerToken, id, request.toMultipartBody())

    override fun deleteUser(bearerToken: String, id: Int): Single<BaseResponse<Nothing>> =
        userApi.deleteUser(bearerToken, id)


    override fun verifyEmail(bearerToken: String, id: Int): Single<BaseResponse<Nothing>> =
        userApi.verifyEmail(bearerToken, id)

    override fun resetPassword(email: String): Single<BaseResponse<Nothing>> =
        userApi.resetPassword(email)

    override fun reIssuanceAccessToken(
        bearerToken: String,
        refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>> =
        userApi.reIssuanceAccessToken(bearerToken, refreshToken)
}

data class RegisterUserRequest(val email: String, val password: String, val nickname: String)
data class LoginRequest(val email: String, val password: String)
data class ReIssuanceTokenRequest(@SerializedName("refresh_token") val refreshToken: String)

data class ModifyUserRequest(
    val image: File? = null,
    val introduce: String = "",
    val location: String = "",
    val careerTitle: String = "",
    val careerContents: String = "",
    val snsGithub: String = "",
    val snsLinkedIn: String = "",
    val snsWeb: String = ""
) {
    fun toMultipartBody(): MultipartBody {
        return MultipartBody.Builder().run {
            if (introduce.isNotEmpty()) addFormDataPart("introduce", introduce)
            if (location.isNotEmpty()) addFormDataPart("location", location)
            if (careerTitle.isNotEmpty()) addFormDataPart("career_title", careerTitle)
            if (careerContents.isNotEmpty()) addFormDataPart("career_contents", careerContents)
            if (snsGithub.isNotEmpty()) addFormDataPart("sns_github", snsGithub)
            if (snsLinkedIn.isNotEmpty()) addFormDataPart("sns_linkedin", snsLinkedIn)
            if (snsWeb.isNotEmpty()) addFormDataPart("sns_web", snsWeb)
            if (image != null) {
                addFormDataPart(
                    "image",
                    image.name,
                    RequestBody.create(MultipartBody.FORM, image)
                )
            }
            build()
        }
    }
}

interface UserRemoteDataSource {
    fun login(email: String, password: String): Single<BaseResponse<UserAuthResponse>>

    fun getUser(bearerToken: String, id: Int): Single<BaseResponse<UserResponse>>

    fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>

    fun modifyUser(
        bearerToken: String,
        id: Int,
        request: ModifyUserRequest
    ): Single<BaseResponse<Nothing>>

    fun deleteUser(bearerToken: String, id: Int): Single<BaseResponse<Nothing>>

    fun verifyEmail(bearerToken: String, id: Int): Single<BaseResponse<Nothing>>

    fun resetPassword(email: String): Single<BaseResponse<Nothing>>

    fun reIssuanceAccessToken(
        bearerToken: String,
        refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>>
}