package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Chatting
import com.iron.espresso.domain.repo.ChatRepository
import io.reactivex.Single
import javax.inject.Inject

class GetChat @Inject constructor(private val chatRepository: ChatRepository) {

    operator fun invoke(studyId: Int, date: Long, first: Boolean): Single<Chatting> {
        return chatRepository.getChat(studyId, date, first)
    }
}