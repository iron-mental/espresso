package com.iron.espresso

import android.content.Context
import com.google.gson.Gson
import com.iron.espresso.model.response.user.UserResponse

object UserHolder {
    private var user: UserResponse? = null

    private val gson by lazy {
        Gson()
    }

    fun set(context: Context, userResponse: UserResponse): Boolean {
        return PrefUtil.setString(
            context,
            PrefUtil.User.FILE_NAME,
            PrefUtil.User.USER_INFO,
            gson.toJson(userResponse)
        )
    }

    fun get(context: Context): UserResponse? {
        return user ?: run {
            val user = PrefUtil.getString(
                context,
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