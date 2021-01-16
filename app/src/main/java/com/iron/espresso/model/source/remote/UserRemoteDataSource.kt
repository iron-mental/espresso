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

    override fun login(email: String, password: String, pushToken: String): Single<BaseResponse<UserAuthResponse>> =
        userApi.login(LoginRequest(email, password, pushToken))

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

    override fun modifyUserImage(
        bearerToken: String,
        id: Int,
        request: ModifyUserImageRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserImage(bearerToken, id, request.toMultipartBody()!!)


    override fun modifyUserInfo(
        bearerToken: String,
        id: Int,
        request: ModifyUserInfoRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserInfo(bearerToken, id, request)

    override fun modifyUserCareer(
        bearerToken: String,
        id: Int,
        request: ModifyUserCareerRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserCareer(bearerToken, id, request)

    override fun modifyUserSns(
        bearerToken: String,
        id: Int,
        request: ModifyUserSnsRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserSns(bearerToken, id, request)

    override fun modifyUserLocation(
        bearerToken: String,
        id: Int,
        request: ModifyUserLocationRequest
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserLocation(bearerToken, id, request)

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
data class LoginRequest(val email: String, val password: String, @SerializedName("push_token") val pushToken: String)
data class ReIssuanceTokenRequest(@SerializedName("refresh_token") val refreshToken: String)

data class ModifyUserImageRequest(
    val image: File? = null
) {
    fun toMultipartBody(): MultipartBody.Part? {
        return if (image != null) {
            MultipartBody.Part.createFormData(
                "image",
                image.name,
                RequestBody.create(MultipartBody.FORM, image)
            )
        } else {
            null
        }
    }
}
data class ModifyUserInfoRequest(
    @SerializedName("nickname") val nickname: String? = null,
    @SerializedName("introduce") val introduce: String? = null
)

data class ModifyUserCareerRequest(
    @SerializedName("career_title") val careerTitle: String,
    @SerializedName("career_contents") val careerContents: String
)

data class ModifyUserSnsRequest(
    @SerializedName("sns_github") val githubUrl: String,
    @SerializedName("sns_linkedin") val linkedInUrl: String,
    @SerializedName("sns_web") val webUrl: String
)

data class ModifyUserLocationRequest(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("sido") val sido: String,
    @SerializedName("sigungu") val sigungu: String,
)




interface UserRemoteDataSource {
    fun login(email: String, password: String, pushToken: String): Single<BaseResponse<UserAuthResponse>>

    fun getUser(bearerToken: String, id: Int): Single<BaseResponse<UserResponse>>

    fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>

    fun deleteUser(bearerToken: String, id: Int): Single<BaseResponse<Nothing>>

    fun verifyEmail(bearerToken: String, id: Int): Single<BaseResponse<Nothing>>

    fun resetPassword(email: String): Single<BaseResponse<Nothing>>

    fun reIssuanceAccessToken(
        bearerToken: String,
        refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>>

    fun modifyUserImage(
        bearerToken: String,
        id: Int,
        request: ModifyUserImageRequest
    ): Single<BaseResponse<Nothing>>

    fun modifyUserInfo(
        bearerToken: String,
        id: Int,
        request: ModifyUserInfoRequest
    ): Single<BaseResponse<Nothing>>

    fun modifyUserCareer(
        bearerToken: String,
        id: Int,
        request: ModifyUserCareerRequest
    ): Single<BaseResponse<Nothing>>

    fun modifyUserSns(
        bearerToken: String,
        id: Int,
        request: ModifyUserSnsRequest
    ): Single<BaseResponse<Nothing>>

    fun modifyUserLocation(
        bearerToken: String,
        id: Int,
        request: ModifyUserLocationRequest
    ): Single<BaseResponse<Nothing>>


}