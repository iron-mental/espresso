package com.iron.espresso.model.source.remote

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single
import javax.inject.Inject


class StudyRemoteDataSourceImpl @Inject constructor(private val studyApi: StudyApi):
    StudyRemoteDataSource {

    override fun getStudyPagingList(sort: String, studyIds: String): Single<BaseResponse<StudyListResponse>> {
        return studyApi.getStudyPagingList(AuthHolder.bearerToken, sort, studyIds)
    }
}

interface StudyRemoteDataSource {
    fun getStudyPagingList(sort: String, studyIds: String): Single<BaseResponse<StudyListResponse>>
}