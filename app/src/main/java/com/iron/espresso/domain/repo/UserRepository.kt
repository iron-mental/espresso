package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.User

interface UserRepository {
    fun getUser(userId: String, userPass: String): User

    fun registerUser(userId: String, userPass: String, nickname: String): Boolean

    fun checkDuplicateEmail(email: String): String

    fun checkDuplicateNickname(nickname: String): String
}