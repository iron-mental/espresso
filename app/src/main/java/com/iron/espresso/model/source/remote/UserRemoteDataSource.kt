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
}

interface UserRemoteDataSource {
    fun getUser(userId: String, userPass: String): UserResponse
}