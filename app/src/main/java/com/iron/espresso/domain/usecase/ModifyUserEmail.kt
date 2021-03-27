package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ModifyUserEmail @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(email: String): Completable {
        return userRepository.modifyUserEmail(email)
    }
}