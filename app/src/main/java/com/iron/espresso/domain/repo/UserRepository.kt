package com.iron.espresso.domain.repo

import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.model.response.MessageResponse
import io.reactivex.Single

interface UserRepository {
    fun login(email: String, password: String): Single<LoginUser>

    fun registerUser(email: String, password: String, nickname: String): Single<MessageResponse>

    fun checkDuplicateEmail(email: String): Single<MessageResponse>

    fun checkDuplicateNickname(nickname: String): Single<MessageResponse>
}