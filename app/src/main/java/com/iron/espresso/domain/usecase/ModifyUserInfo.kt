package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ModifyUserInfo @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(nickname: String?, introduce: String): Single<Boolean> {
        return userRepository.modifyUserInfo(nickname, introduce)
    }
}