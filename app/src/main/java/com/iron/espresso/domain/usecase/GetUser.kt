package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.MessageResponse
import io.reactivex.Single

class GetUser(private val userRepository: UserRepository) {
    operator fun invoke(userId: String, userPass: String): Single<User> =
        userRepository.getUser(userId, userPass)
}