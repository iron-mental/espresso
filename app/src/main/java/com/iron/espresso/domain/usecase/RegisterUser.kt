package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.MessageResponse
import io.reactivex.Single

class RegisterUser(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String, nickname: String): Single<MessageResponse> =
        userRepository.registerUser(email, userPass, nickname)
}