package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.ApplyApi
import com.iron.espresso.model.api.HandleApplyRequest
import com.iron.espresso.model.api.ModifyStudyApplyRequest
import com.iron.espresso.model.api.RegisterStudyApplyRequest
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.apply.ApplyDetailResponse
import com.iron.espresso.model.response.apply.ApplyListResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class ApplyRemoteDataSourceImpl @Inject constructor(private val applyApi: ApplyApi) :
    ApplyRemoteDataSource {
    override fun registerApply(
        studyId: Int,
        message: String,
    ): Single<BaseResponse<Nothing>> {
        return applyApi.registerApply(studyId = studyId, body = RegisterStudyApplyRequest(message))
    }

    override fun getApplyByOwner(
        studyId: Int,
        applyId: Int
    ): Single<BaseResponse<ApplyDetailResponse>> {
        return applyApi.getApplyByOwner(studyId = studyId, applyId = applyId)
    }

    override fun getApplyByApplier(
        studyId: Int,
        userId: Int
    ): Single<BaseResponse<ApplyDetailResponse>> {
        return applyApi.getApplyByApplier(studyId = studyId, userId = userId)
    }

    override fun getApplyList(studyId: Int): Single<BaseResponse<ApplyListResponse>> {
        return applyApi.getApplyList(studyId = studyId)
    }

    override fun getMyApplyList(): Single<BaseResponse<ApplyListResponse>> {
        return applyApi.getMyApplyList()
    }

    override fun modifyApply(
        studyId: Int,
        applyId: Int,
        message: String
    ): Single<BaseResponse<Nothing>> {
        return applyApi.modifyApply(
            studyId = studyId,
            applyId = applyId,
            body = ModifyStudyApplyRequest(message)
        )
    }

    override fun deleteApply(studyId: Int, applyId: Int): Single<BaseResponse<Nothing>> {
        return applyApi.deleteApply(studyId = studyId, applyId = applyId)
    }

    override fun handleApply(studyId: Int, applyId: Int, allow: Boolean): Single<BaseResponse<Nothing>> {
        return applyApi.handleApply(studyId = studyId, applyId = applyId, body = HandleApplyRequest(allow))
    }
}

interface ApplyRemoteDataSource {
    fun registerApply(
        studyId: Int,
        message: String
    ): Single<BaseResponse<Nothing>>

    fun getApplyByOwner(
        studyId: Int,
        applyId: Int
    ): Single<BaseResponse<ApplyDetailResponse>>

    fun getApplyByApplier(
        studyId: Int,
        userId: Int
    ): Single<BaseResponse<ApplyDetailResponse>>

    fun getApplyList(studyId: Int): Single<BaseResponse<ApplyListResponse>>

    fun getMyApplyList(): Single<BaseResponse<ApplyListResponse>>

    fun modifyApply(
        studyId: Int,
        applyId: Int,
        message: String
    ): Single<BaseResponse<Nothing>>

    fun deleteApply(
        studyId: Int,
        applyId: Int
    ): Single<BaseResponse<Nothing>>

    fun handleApply(
        studyId: Int,
        applyId: Int,
        allow: Boolean
    ): Single<BaseResponse<Nothing>>
}