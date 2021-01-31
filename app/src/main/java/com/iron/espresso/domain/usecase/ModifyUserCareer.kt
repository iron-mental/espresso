package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class ModifyUserCareer @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(title: String, contents: String): Completable {
        return userRepository.modifyUserCareer(title, contents)
    }
}