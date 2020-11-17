package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.model.response.BaseResponse
import io.reactivex.Single

interface UserRepository {
    fun login(email: String, password: String): Single<User>

    fun registerUser(email: String, password: String, nickname: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>
}