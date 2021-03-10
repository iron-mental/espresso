package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import javax.inject.Inject

class VerifyEmail @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke() =
        userRepository.verifyEmail()
}