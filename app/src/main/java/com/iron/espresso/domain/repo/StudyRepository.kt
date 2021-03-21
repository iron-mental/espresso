package com.iron.espresso.domain.repo

import com.iron.espresso.model.api.ModifyStudyRequest
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.*
import io.reactivex.Single

interface StudyRepository {
    fun getStudyPagingList(studyIds: List<Int>, option: String): Single<StudyListResponse>

    fun getStudyList(category: String, sort: String): Single<StudyListResponse>

    fun getSearchStudyList(word: String): Single<StudyListResponse>

    fun getHotSearchKeyword(): Single<List<HotSearchKeywordResponse>>

    fun leaveStudy(studyId: Int): Single<BaseResponse<Nothing>>

    fun getStudyDetail(studyId: Int): Single<StudyDetailResponse>

    fun getMyStudyList(userId: Int): Single<MyStudyListResponse>

    fun deleteStudy(studyId: Int): Single<BaseResponse<Nothing>>

    fun delegateStudyLeader(studyId: Int, newLeader: Int): Single<BaseResponse<Nothing>>

    fun modifyStudy(studyId: Int, request: ModifyStudyRequest): Single<BaseResponse<Nothing>>

    fun getStudyCategory(): Single<List<String>>
}