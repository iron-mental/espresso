package com.iron.espresso.model.source.remote

import com.iron.espresso.AuthHolder
import com.iron.espresso.model.api.DelegateRequest
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.HotSearchKeywordResponse
import com.iron.espresso.model.response.study.MyStudyListResponse
import com.iron.espresso.model.response.study.StudyDetailResponse
import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single
import javax.inject.Inject


class StudyRemoteDataSourceImpl @Inject constructor(private val studyApi: StudyApi) :
    StudyRemoteDataSource {

    override fun getStudyPagingList(
        studyIds: List<Int>,
        option: String
    ): Single<BaseResponse<StudyListResponse>> {
        return studyApi.getStudyPagingList(
            AuthHolder.bearerToken,
            studyIds.joinToString(","),
            option
        )
    }

    override fun getStudyList(
        category: String,
        sort: String
    ): Single<BaseResponse<StudyListResponse>> {
        return studyApi.getStudyList(AuthHolder.bearerToken, category, sort)
    }

    override fun getSearchStudyList(
        word: String
    ): Single<BaseResponse<StudyListResponse>> =
        studyApi.getSearchStudyList(
            word = word
        )

    override fun getHotSearchKeyword(): Single<BaseResponse<List<HotSearchKeywordResponse>>> =
        studyApi.getHotSearchKeyword()

    override fun leaveStudy(studyId: Int): Single<BaseResponse<Nothing>> {
        return studyApi.leaveStudy(studyId = studyId)
    }

    override fun getStudyDetail(studyId: Int): Single<BaseResponse<StudyDetailResponse>> {
        return studyApi.getStudyDetail(studyId = studyId)
    }

    override fun getMyStudyList(userId: Int): Single<BaseResponse<MyStudyListResponse>> {
        return studyApi.getMyStudyList(userId = userId)
    }

    override fun deleteStudy(studyId: Int): Single<BaseResponse<Nothing>> {
        return studyApi.deleteStudy(studyId = studyId)
    }

    override fun delegateStudyLeader(studyId: Int, newLeader: Int): Single<BaseResponse<Nothing>> {
        return studyApi.delegateStudyLeader(studyId = studyId, body = DelegateRequest(newLeader))
    }

    override fun getStudyCategory(): Single<BaseResponse<List<String>>> {
        return studyApi.getStudyCategory()
    }
}

interface StudyRemoteDataSource {
    fun getStudyPagingList(
        studyIds: List<Int>,
        option: String
    ): Single<BaseResponse<StudyListResponse>>

    fun getStudyList(
        category: String,
        sort: String // new, length
    ): Single<BaseResponse<StudyListResponse>>

    fun getSearchStudyList(
        word: String
    ): Single<BaseResponse<StudyListResponse>>

    fun getHotSearchKeyword(): Single<BaseResponse<List<HotSearchKeywordResponse>>>

    fun leaveStudy(studyId: Int): Single<BaseResponse<Nothing>>

    fun getStudyDetail(studyId: Int): Single<BaseResponse<StudyDetailResponse>>

    fun getMyStudyList(userId: Int): Single<BaseResponse<MyStudyListResponse>>

    fun deleteStudy(studyId: Int): Single<BaseResponse<Nothing>>

    fun delegateStudyLeader(studyId: Int, newLeader: Int): Single<BaseResponse<Nothing>>

    fun getStudyCategory(): Single<BaseResponse<List<String>>>
}