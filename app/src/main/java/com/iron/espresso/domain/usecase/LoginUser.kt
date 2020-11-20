package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String): Single<User> =
        userRepository.login(email, userPass)
}