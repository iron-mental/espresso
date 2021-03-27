package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(id: Int): Single<User> =
        userRepository.getUser(id)
}