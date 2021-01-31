package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.ApplyRepository
import com.iron.espresso.model.api.RegisterStudyApplyRequest
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.source.remote.ApplyRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class ApplyRepositoryImpl @Inject constructor(private val applyRemoteDataSource: ApplyRemoteDataSource) :
    ApplyRepository {
    override fun registerApply(
        studyId: Int,
        request: RegisterStudyApplyRequest
    ): Single<BaseResponse<Nothing>> =
        applyRemoteDataSource.registerApply(studyId, request)
}