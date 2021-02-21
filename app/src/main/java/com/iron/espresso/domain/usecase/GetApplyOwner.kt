package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.ApplyDetail
import com.iron.espresso.domain.repo.ApplyRepository
import io.reactivex.Single
import javax.inject.Inject

class GetApplyOwner @Inject constructor(private val applyRepository: ApplyRepository) {

    operator fun invoke(studyId: Int, applyId: Int): Single<ApplyDetail> {
        return applyRepository.getApplyByOwner(studyId, applyId)
    }
}