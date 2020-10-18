package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.User

interface UserRepository {
    fun getUser(userId: String, userPass: String): User
}