package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Apply
import com.iron.espresso.domain.repo.ApplyRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMyApplyList @Inject constructor(private val applyRepository: ApplyRepository) {

    operator fun invoke(): Single<List<Apply>> {
        return applyRepository.getMyApplyList()
    }
}