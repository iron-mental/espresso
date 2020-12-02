package com.iron.espresso

import com.google.gson.Gson
import com.iron.espresso.model.response.user.UserAuthResponse

object AuthHolder {

    private var userAuthResponse: UserAuthResponse? = null

    private val gson by lazy {
        Gson()
    }

    val bearerToken: String
        get() = get()?.accessToken?.let {
            "Bearer $it"
        } ?: run {
            ""
        }

    private val accessToken: String
        get() = get()?.accessToken.orEmpty()

    val refreshToken: String
        get() = get()?.refreshToken.orEmpty()

    val id: Int?
        get() = get()?.id

    fun set(authResponse: UserAuthResponse): Boolean {
        userAuthResponse = authResponse
        return PrefUtil.setString(
            App.instance.context(),
            PrefUtil.Auth.FILE_NAME,
            PrefUtil.Auth.AUTH_TOKEN,
            gson.toJson(authResponse)
        )
    }

    fun updateAccessToken(token: String): Boolean {
        val newAuth = get()?.copy(accessToken = token)
        if (newAuth != null) {
            return set(newAuth)
        }
        return false
    }

    private fun get(): UserAuthResponse? {
        return userAuthResponse ?: run {
            val userAuthResponse = PrefUtil.getString(
                App.instance.context(),
                PrefUtil.Auth.FILE_NAME,
                PrefUtil.Auth.AUTH_TOKEN
            )

            if (userAuthResponse != null) {
                gson.fromJson(
                    userAuthResponse,
                    UserAuthResponse::class.java
                ).also {
                    this@AuthHolder.userAuthResponse = it
                }
            } else {
                null
            }
        }
    }
}