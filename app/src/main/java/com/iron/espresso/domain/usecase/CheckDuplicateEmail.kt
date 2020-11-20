package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Message
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class CheckDuplicateEmail @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String): Single<Message> =
        userRepository.checkDuplicateEmail(email).map { it.toMessage() }
}