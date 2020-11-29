package com.iron.espresso.domain.repo

import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import io.reactivex.Single

interface UserRepository {
    fun login(email: String, password: String, pushToken: String): Single<BaseResponse<UserAuthResponse>>

    fun registerUser(email: String, password: String, nickname: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>
}