package com.iron.espresso.model.source.remote

import com.google.gson.annotations.SerializedName
import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.address.AddressResponse
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

    override fun login(
        email: String,
        password: String,
        pushToken: String
    ): Single<BaseResponse<UserAuthResponse>> =
        userApi.login(LoginRequest(email, password, pushToken))

    override fun getUser(id: Int): Single<BaseResponse<UserResponse>> =
        userApi.getUser(id = id)

    override fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>> =
        userApi.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>> =
        userApi.checkDuplicateNickname(nickname)

    override fun deleteUser(email: String, password: String): Single<BaseResponse<Nothing>> =
        userApi.deleteUser(body = DeleteUserRequest(email, password))

    override fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>> =
        userApi.registerUser(RegisterUserRequest(email, password, nickname))

    override fun logout(): Single<BaseResponse<Nothing>> =
        userApi.logout()

    override fun modifyUserImage(image: File?): Single<BaseResponse<Nothing>> =
        userApi.modifyUserImage(image = ModifyUserImageRequest(image).toMultipartBody())

    override fun modifyUserInfo(
        nickname: String?,
        introduce: String
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserInfo(body = ModifyUserInfoRequest(nickname, introduce))

    override fun modifyUserCareer(title: String, contents: String): Single<BaseResponse<Nothing>> =
        userApi.modifyUserCareer(body = ModifyUserCareerRequest(title, contents))

    override fun modifyUserSns(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserSns(body = ModifyUserSnsRequest(githubUrl, linkedInUrl, webUrl))

    override fun modifyUserEmail(email: String): Single<BaseResponse<Nothing>> =
        userApi.modifyUserEmail(body = ModifyUserEmailRequest(email))

    override fun modifyUserLocation(
        latitude: Double,
        longitude: Double,
        sido: String,
        sigungu: String
    ): Single<BaseResponse<Nothing>> =
        userApi.modifyUserLocation(
            body = ModifyUserLocationRequest(
                latitude,
                longitude,
                sido,
                sigungu
            )
        )

    override fun getAddressList(): Single<BaseResponse<List<AddressResponse>>> =
        userApi.getAddressList()


    override fun verifyEmail(): Single<BaseResponse<Nothing>> =
        userApi.verifyEmail()

    override fun resetPassword(email: String): Single<BaseResponse<Nothing>> =
        userApi.resetPassword(email)

    override fun reIssuanceAccessToken(
        bearerToken: String,
        refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>> =
        userApi.reIssuanceAccessToken(bearerToken, refreshToken)
}

data class RegisterUserRequest(val email: String, val password: String, val nickname: String)
data class LoginRequest(
    val email: String,
    val password: String,
    @SerializedName("push_token") val pushToken: String,
    @SerializedName("device") val deviceToken: String = "android"
)

data class LogoutRequest(
    @SerializedName("id") val id: Int
)

data class DeleteUserRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

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

data class ModifyUserEmailRequest(
    @SerializedName("email") val email: String,
)

data class ModifyUserLocationRequest(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("sido") val sido: String,
    @SerializedName("sigungu") val sigungu: String,
)


interface UserRemoteDataSource {
    fun login(
        email: String,
        password: String,
        pushToken: String
    ): Single<BaseResponse<UserAuthResponse>>

    fun getUser(id: Int): Single<BaseResponse<UserResponse>>

    fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>>

    fun logout(): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>

    fun deleteUser(email: String, password: String): Single<BaseResponse<Nothing>>

    fun verifyEmail(): Single<BaseResponse<Nothing>>

    fun resetPassword(email: String): Single<BaseResponse<Nothing>>

    fun reIssuanceAccessToken(
        bearerToken: String,
        refreshToken: ReIssuanceTokenRequest
    ): Single<BaseResponse<AccessTokenResponse>>

    fun modifyUserImage(
        image: File?
    ): Single<BaseResponse<Nothing>>

    fun modifyUserInfo(
        nickname: String?,
        introduce: String
    ): Single<BaseResponse<Nothing>>

    fun modifyUserEmail(
        email: String
    ): Single<BaseResponse<Nothing>>

    fun modifyUserCareer(
        title: String,
        contents: String
    ): Single<BaseResponse<Nothing>>

    fun modifyUserSns(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Single<BaseResponse<Nothing>>

    fun modifyUserLocation(
        latitude: Double,
        longitude: Double,
        sido: String,
        sigungu: String
    ): Single<BaseResponse<Nothing>>

    fun getAddressList(): Single<BaseResponse<List<AddressResponse>>>

}