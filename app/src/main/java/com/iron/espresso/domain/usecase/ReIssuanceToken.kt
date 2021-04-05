package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.AccessToken
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single

class ReIssuanceToken(private val userRepository: UserRepository) {
    operator fun invoke(refreshToken: String): Single<AccessToken> =
        userRepository.reIssuanceAccessToken(refreshToken)
}