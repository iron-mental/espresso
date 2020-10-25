package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.UserResponse
import io.reactivex.Single


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
}

data class RegisterUserRequest(val email: String, val password: String, val nickname: String)
data class LoginRequest(val email: String, val password: String)

interface UserRemoteDataSource {
    fun login(email: String, password: String): Single<UserResponse>

    fun getUser(id: Int): Single<UserResponse>

    fun registerUser(email: String, password: String, nickname: String): Single<MessageResponse>

    fun checkDuplicateEmail(email: String): Single<MessageResponse>

    fun checkDuplicateNickname(nickname: String): Single<MessageResponse>
}