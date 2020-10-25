package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.UserResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {

    override fun login(email: String, password: String): Single<UserResponse> =
        userApi.login(LoginRequest(email, password))

    override fun getUser(id: Int): Single<UserResponse> =
        userApi.getUser(id)

    override fun checkDuplicateEmail(email: String): Single<MessageResponse> =
        userApi.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<MessageResponse> =
        userApi.checkDuplicateNickname(nickname)

    override fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<MessageResponse> =
        userApi.registerUser(RegisterUserRequest(email, password, nickname))

    override fun modifyUser(request: ModifyUserRequest): Single<UserResponse> =
        userApi.modifyUser(request.toMultipartBody())
}

data class RegisterUserRequest(val email: String, val password: String, val nickname: String)
data class LoginRequest(val email: String, val password: String)

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
    fun toMultipartBody(): RequestBody {
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
                    image.name,
                    image.name,
                    RequestBody.create(MultipartBody.FORM, image)
                )
            }
            build()
        }
    }
}

interface UserRemoteDataSource {
    fun login(email: String, password: String): Single<UserResponse>

    fun getUser(id: Int): Single<UserResponse>

    fun registerUser(email: String, password: String, nickname: String): Single<MessageResponse>

    fun checkDuplicateEmail(email: String): Single<MessageResponse>

    fun checkDuplicateNickname(nickname: String): Single<MessageResponse>

    fun modifyUser(request: ModifyUserRequest): Single<UserResponse>
}