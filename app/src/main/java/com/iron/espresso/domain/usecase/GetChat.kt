package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Chatting
import com.iron.espresso.domain.repo.StudyRepository
import io.reactivex.Single
import javax.inject.Inject

class GetChat @Inject constructor(private val studyRepository: StudyRepository) {

    operator fun invoke(studyId: Int, date: Long, first: Boolean): Single<Chatting> {
        return studyRepository.getChat(studyId, date, first)
    }
}