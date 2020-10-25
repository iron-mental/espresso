package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.model.response.MessageResponse
import io.reactivex.Single

interface UserRepository {
    fun getUser(userId: String, password: String): Single<User>

    fun registerUser(userId: String, password: String, nickname: String): Single<MessageResponse>

    fun checkDuplicateEmail(email: String): Single<MessageResponse>

    fun checkDuplicateNickname(nickname: String): Single<MessageResponse>
}