package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String): Single<LoginUser> =
        userRepository.login(email, userPass)
}