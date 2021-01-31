package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Apply
import com.iron.espresso.domain.repo.ApplyRepository
import io.reactivex.Single
import javax.inject.Inject

class GetApplyList @Inject constructor(private val applyRepository: ApplyRepository) {

    operator fun invoke(studyId: Int): Single<List<Apply>> {
        return applyRepository.getApplyList(studyId)
    }
}