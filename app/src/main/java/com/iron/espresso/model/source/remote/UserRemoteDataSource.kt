package com.iron.espresso.model.source.remote

import com.google.gson.JsonObject
import com.iron.espresso.model.api.UserApi
import com.iron.espresso.model.response.UserResponse


class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {

    override fun getUser(userId: String, userPass: String): UserResponse {
        val body = JsonObject().apply {
            addProperty("email", userId)
            addProperty("password", userPass)
        }

        return userApi.getUser(body).execute().body() ?: error(
            userApi.getUser(body).execute().message()
        )
    }

    override fun checkDuplicateEmail(email: String): String =
        userApi.checkDuplicateEmail(email).execute().body() ?: error("실패")


    override fun checkDuplicateNickname(nickname: String): String =
        userApi.checkDuplicateNickname(nickname).execute().body() ?: error("실패")


    override fun registerUser(userId: String, userPass: String, nickname: String): Boolean {
        val body = JsonObject().apply {
            addProperty("email", userId)
            addProperty("password", userPass)
            addProperty("nickname", nickname)
        }
        return userApi.registerUser(body).request().isHttps
    }
}

interface UserRemoteDataSource {
    fun getUser(userId: String, userPass: String): UserResponse

    fun registerUser(userId: String, userPass: String, nickname: String): Boolean

    fun checkDuplicateEmail(email: String): String

    fun checkDuplicateNickname(nickname: String): String
}