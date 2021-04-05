package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LogoutUser @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Single<Pair<Boolean, String>> =
        userRepository.logout()
}