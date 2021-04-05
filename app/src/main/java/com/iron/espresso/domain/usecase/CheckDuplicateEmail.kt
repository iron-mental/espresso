package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CheckDuplicateEmail @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String): Single<Boolean> =
        userRepository.checkDuplicateEmail(email)
}