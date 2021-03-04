package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.ApplyRepository
import io.reactivex.Single
import javax.inject.Inject

class HandleApply @Inject constructor(private val applyRepository: ApplyRepository) {

    operator fun invoke(studyId: Int, applyId: Int, allow: Boolean): Single<Pair<Boolean, String>> {
        return applyRepository.handleApply(studyId, applyId, allow)
    }
}