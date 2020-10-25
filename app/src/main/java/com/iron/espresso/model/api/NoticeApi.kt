package com.iron.espresso.model.api

import retrofit2.http.*

data class RegisterNoticeRequest(
    val title: String,
    val contents: String,
    val pinned: Boolean
)

data class ModifyNoticeRequest(
    val title: String,
    val contents: String,
    val pinned: Boolean
)

interface NoticeApi {

    @POST("/v1/study/{study_id}/notice")
    fun registerNotice(
        @Path(value = "study_id") studyId: Int,
        @Body body: RegisterNoticeRequest
    )

    @GET("/v1/study/{study_id}/notice/{notice_id}")
    fun getNotice(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "notice_id") noticeId: Int
    )

    @PATCH("/v1/study/{study_id}/notice/{notice_id}")
    fun modifyNotice(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "notice_id") noticeId: Int,
        @Body body: ModifyNoticeRequest
    )

    @DELETE("/v1/study/{study_id}/notice/{notice_id}")
    fun deleteNotice(
        @Path(value = "study_id") studyId: Int,
        @Path(value = "notice_id") noticeId: Int
    )
}