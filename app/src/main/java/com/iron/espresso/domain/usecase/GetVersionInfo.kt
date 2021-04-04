package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Version
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single

class GetVersionInfo(private val userRepository: UserRepository) {
    operator fun invoke(version: String): Single<Version> =
        userRepository.getVersionInfo(version)
}