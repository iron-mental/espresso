package com.iron.espresso.model.api

import com.iron.espresso.model.response.MessageResponse
import io.reactivex.Single
import retrofit2.http.*


interface ApplyApi {

    @POST("/v1/study/{study_id}/apply")
    fun registerApply(
        @Path(value = "study_id") studyId: Int
    )

    @GET("/v1/study/{study_id}/apply/{apply_id}")
    fun getApply(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "apply_id") applyId: Int
    )

    @PATCH("/v1/study/{study_id}/apply/{apply_id}")
    fun modifyApply(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "apply_id") applyId: Int
    )

    @DELETE("/v1/study/{study_id}/apply/{apply_id}")
    fun deleteApply(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "apply_id") applyId: Int
    ): Single<MessageResponse>
}