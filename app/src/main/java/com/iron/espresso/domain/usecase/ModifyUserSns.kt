package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ModifyUserSns @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Completable {
        return userRepository.modifyUserSns(githubUrl, linkedInUrl, webUrl)
    }
}