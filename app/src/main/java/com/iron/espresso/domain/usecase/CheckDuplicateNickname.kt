package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Message
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class CheckDuplicateNickname @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(nickname: String): Single<Message> =
        userRepository.checkDuplicateNickname(nickname).map { Message(it.result, it.message.orEmpty()) }
}