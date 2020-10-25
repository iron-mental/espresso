package com.iron.espresso.model.source.remote

import com.google.gson.JsonObject
import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.UserResponse
import io.reactivex.Single


class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {

    override fun getUser(userId: String, password: String): Single<UserResponse> {
        val body = JsonObject().apply {
            addProperty("email", userId)
            addProperty("password", password)
        }

        return userApi.getUser(body)
    }

    override fun checkDuplicateEmail(email: String): Single<MessageResponse> =
        userApi.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<MessageResponse> =
        userApi.checkDuplicateNickname(nickname)

    override fun registerUser(userId: String, password: String, nickname: String): Single<MessageResponse> {
        val body = JsonObject().apply {
            addProperty("email", userId)
            addProperty("password", password)
            addProperty("nickname", nickname)
        }
        return userApi.registerUser(body)
    }
}

interface UserRemoteDataSource {
    fun getUser(userId: String, password: String): Single<UserResponse>

    fun registerUser(userId: String, password: String, nickname: String): Single<MessageResponse>

    fun checkDuplicateEmail(email: String): Single<MessageResponse>

    fun checkDuplicateNickname(nickname: String): Single<MessageResponse>
}