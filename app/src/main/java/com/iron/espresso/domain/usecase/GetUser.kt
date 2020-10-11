package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository

class GetUser(private val userRepository: UserRepository) {
    operator fun invoke(userId: String, userPass: String): User =
        userRepository.getUser(userId, userPass)
}

