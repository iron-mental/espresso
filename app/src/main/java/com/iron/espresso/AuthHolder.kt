package com.iron.espresso

import android.content.Context
import com.google.gson.Gson
import com.iron.espresso.model.response.user.UserAuthResponse

object AuthHolder {

    private var userAuthResponse: UserAuthResponse? = null

    private val gson by lazy {
        Gson()
    }



    fun set(context: Context, authResponse: UserAuthResponse): Boolean {
        return PrefUtil.setString(
            context,
            PrefUtil.Auth.FILE_NAME,
            PrefUtil.Auth.AUTH_TOKEN,
            gson.toJson(authResponse)
        )
    }

    fun get(context: Context): UserAuthResponse? {
        return userAuthResponse ?: run {
            val userAuthResponse = PrefUtil.getString(
                context,
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