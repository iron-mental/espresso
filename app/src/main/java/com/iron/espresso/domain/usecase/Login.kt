package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single

class Login(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String): Single<User> =
        userRepository.login(email, userPass)
}