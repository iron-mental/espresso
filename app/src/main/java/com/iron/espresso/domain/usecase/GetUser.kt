package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository

class GetUser(private val userRepository: UserRepository) {
    operator fun invoke(userId: String, userPass: String): User =
        userRepository.getUser(userId, userPass)
}


import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.repo.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUser @Inject constructor(private val profileRepository: ProfileRepository) {

    operator fun invoke(userId: String): Single<GithubUser> {
        return profileRepository.getUser(userId)
    }
}