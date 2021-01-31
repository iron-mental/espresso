package com.iron.espresso.domain.repo

import com.iron.espresso.model.api.RegisterStudyApplyRequest
import com.iron.espresso.model.response.BaseResponse
import io.reactivex.Single

interface ApplyRepository {
    fun registerApply(
        studyId: Int,
        request: RegisterStudyApplyRequest
    ): Single<BaseResponse<Nothing>>
}