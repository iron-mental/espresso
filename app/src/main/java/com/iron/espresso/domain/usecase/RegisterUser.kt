package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import io.reactivex.rxjava3.core.Single

class RegisterUser(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String, nickname: String): Single<BaseResponse<Nothing>> =
        userRepository.registerUser(email, userPass, nickname)
}