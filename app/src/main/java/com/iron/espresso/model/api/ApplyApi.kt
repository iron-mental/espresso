package com.iron.espresso.model.api

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.apply.ApplyDetailResponse
import com.iron.espresso.model.response.apply.ApplyListResponse
import io.reactivex.Single
import retrofit2.http.*

data class RegisterStudyApplyRequest(
    val message: String
)

data class ModifyStudyApplyRequest(
    val message: String
)

interface ApplyApi {

    @POST("/v1/study/{study_id}/apply")
    fun registerApply(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int,
        @Body body: RegisterStudyApplyRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/study/{study_id}/apply/{apply_id}")
    fun getApplyByOwner(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int,
        @Path("apply_id") applyId: Int
    ): Single<BaseResponse<ApplyDetailResponse>>

    @GET("/v1/study/{study_id}/applyUser/{user_id}")
    fun getApplyByApplier(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int,
        @Path("user_id") userId: Int
    ): Single<BaseResponse<ApplyDetailResponse>>

    @GET("/v1/study/{study_id}/apply")
    fun getApplyList(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int
    ): Single<BaseResponse<ApplyListResponse>>

    @GET("/v1/user/{id}/apply")
    fun getMyApplyList(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("id") userId: Int = AuthHolder.requireId()
    ): Single<BaseResponse<ApplyListResponse>>

    @PUT("/v1/study/{study_id}/apply/{apply_id}")
    fun modifyApply(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int,
        @Path("apply_id") applyId: Int,
        @Body body: ModifyStudyApplyRequest
    ): Single<BaseResponse<Nothing>>

    @DELETE("/v1/study/{study_id}/apply/{apply_id}")
    fun deleteApply(
        @Header("Authorization") bearerToken: String = AuthHolder.bearerToken,
        @Path("study_id") studyId: Int,
        @Path("apply_id") applyId: Int
    ): Single<BaseResponse<Nothing>>
}