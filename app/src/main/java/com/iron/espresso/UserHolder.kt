package com.iron.espresso

import com.google.gson.Gson
import com.iron.espresso.model.response.user.UserResponse

object UserHolder {
    private var user: UserResponse? = null

    private val gson by lazy {
        Gson()
    }

    fun set(userResponse: UserResponse): Boolean {
        user = userResponse
        return PrefUtil.setString(
            App.instance.context(),
            PrefUtil.User.FILE_NAME,
            PrefUtil.User.USER_INFO,
            gson.toJson(userResponse)
        )
    }

    fun get(): UserResponse? {
        return user ?: run {
            val user = PrefUtil.getString(
                App.instance.context(),
                PrefUtil.User.FILE_NAME,
                PrefUtil.User.USER_INFO
            )

            if (user != null) {
                gson.fromJson(
                    user,
                    UserResponse::class.java
                ).also {
                    this@UserHolder.user = it
                }
            } else {
                null
            }
        }
    }
}