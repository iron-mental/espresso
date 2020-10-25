package com.iron.espresso.model.api

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyApi {

    @POST("/v1/study")
    fun registerStudy(

    )

    @GET("/v1/study/{study_id}")
    fun getStudy(
        @Path(value = "study_id") studyId: Int
    )

    @PATCH("/v1/study/{study_id}")
    fun modifyStudy(
        @Path(value = "study_id") studyId: Int
    )

    @GET("/v1/study/mystudy/{user_id}")
    fun getMyStudyList(
        @Path(value = "user_id") userId: Int
    )

    @GET("/v1/study/{category}/{sort}")
    fun getStudyList(
        @Path(value = "category") category: String,
        @Path(value = "sort") sort: String // new, length
    )
}