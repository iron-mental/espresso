package com.iron.espresso

import com.google.gson.Gson
import com.iron.espresso.domain.entity.User

object UserHolder {
    private var user: User? = null

    private val gson by lazy {
        Gson()
    }

    fun set(user: User): Boolean {
        this.user = user
        return PrefUtil.setString(
            App.instance.context(),
            PrefUtil.User.FILE_NAME,
            PrefUtil.User.USER_INFO,
            gson.toJson(user)
        )
    }

    fun get(): User? {
        return user ?: run {
            val user = PrefUtil.getString(
                App.instance.context(),
                PrefUtil.User.FILE_NAME,
                PrefUtil.User.USER_INFO
            )

            if (user != null) {
                gson.fromJson(
                    user,
                    User::class.java
                ).also {
                    this@UserHolder.user = it
                }
            } else {
                null
            }
        }
    }
}