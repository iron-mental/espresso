package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.ApplyDetail
import com.iron.espresso.domain.repo.ApplyRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetApplyByApplier @Inject constructor(private val applyRepository: ApplyRepository) {

    operator fun invoke(studyId: Int, applyId: Int): Single<ApplyDetail> {
        return applyRepository.getApplyByApplier(studyId, applyId)
    }
}