package com.iron.espresso.model.source.remote

import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single
import javax.inject.Inject


class StudyRemoteDataSourceImpl @Inject constructor(private val studyApi: StudyApi) :
    StudyRemoteDataSource {

    override fun getStudyPagingList(
        sort: String,
        studyIds: List<Int>
    ): Single<BaseResponse<StudyListResponse>> {
        return studyApi.getStudyPagingList(AuthHolder.bearerToken, sort, studyIds.joinToString(","))
    }

    override fun getStudyList(
        category: String,
        sort: String
    ): Single<BaseResponse<StudyListResponse>> {
        return studyApi.getStudyList(AuthHolder.bearerToken, category, sort)
    }
}

interface StudyRemoteDataSource {
    fun getStudyPagingList(
        sort: String,
        studyIds: List<Int>
    ): Single<BaseResponse<StudyListResponse>>

    fun getStudyList(
        category: String,
        sort: String // new, length
    ): Single<BaseResponse<StudyListResponse>>
}