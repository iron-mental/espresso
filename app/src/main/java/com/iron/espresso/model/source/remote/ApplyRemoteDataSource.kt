package com.iron.espresso.model.source.remote

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.api.ApplyApi
import com.iron.espresso.model.api.RegisterStudyApplyRequest
import com.iron.espresso.model.response.BaseResponse
import io.reactivex.Single
import javax.inject.Inject


class ApplyRemoteDataSourceImpl @Inject constructor(private val applyApi: ApplyApi) :
    ApplyRemoteDataSource {
    override fun registerApply(
        studyId: Int,
        request: RegisterStudyApplyRequest
    ): Single<BaseResponse<Nothing>> {
        return applyApi.registerApply(AuthHolder.bearerToken, studyId, request)
    }

}

interface ApplyRemoteDataSource {
    fun registerApply(
        studyId: Int,
        request: RegisterStudyApplyRequest
    ): Single<BaseResponse<Nothing>>
}