package com.iron.espresso.model.api

import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.notice.NoticeDetailResponse
import com.iron.espresso.model.response.notice.NoticeListResponse
import io.reactivex.Single
import retrofit2.http.*

data class RegisterNoticeRequest(
    val title: String,
    val contents: String,
    val pinned: Boolean
)

data class ModifyNoticeRequest(
    val title: String? = null,
    val contents: String? = null,
    val pinned: Boolean? = null
)

interface NoticeApi {

    @POST("/v1/study/{study_id}/notice")
    fun registerNotice(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int,
        @Body body: RegisterNoticeRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/study/{study_id}/notice/{notice_id}")
    fun getNotice(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int,
        @Path("notice_id") noticeId: Int
    ): Single<BaseResponse<NoticeDetailResponse>>

    @GET("/v1/study/{study_id}/notice/")
    fun getNoticeList(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int
    ): Single<BaseResponse<NoticeListResponse>>

    @GET("/v1/study/1/notice/paging/list")
    fun getNoticeList(
        @Header("Authorization") bearerToken: String,
        @Query("values") noticeIds: String
    ): Single<BaseResponse<NoticeListResponse>>

    @PUT("/v1/study/{study_id}/notice/{notice_id}")
    fun modifyNotice(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int,
        @Path("notice_id") noticeId: Int,
        @Body body: ModifyNoticeRequest
    ): Single<BaseResponse<Nothing>>

    @DELETE("/v1/study/{study_id}/notice/{notice_id}")
    fun deleteNotice(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int,
        @Path("notice_id") noticeId: Int
    ): Single<BaseResponse<Nothing>>
}