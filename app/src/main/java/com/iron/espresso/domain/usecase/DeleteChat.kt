package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.ChatRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteChat @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(studyId: Int): Completable {
        return chatRepository.delete(studyId)
    }
}