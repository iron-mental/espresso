package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.model.api.ModifyStudyRequest
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.HotSearchKeywordResponse
import com.iron.espresso.model.response.study.MyStudyListResponse
import com.iron.espresso.model.response.study.StudyDetailResponse
import com.iron.espresso.model.response.study.StudyListResponse
import com.iron.espresso.model.source.remote.StudyRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class StudyRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudyRemoteDataSource
) : StudyRepository {

    override fun getStudyPagingList(
        studyIds: List<Int>,
        option: String
    ): Single<StudyListResponse> {
        return remoteDataSource.getStudyPagingList(studyIds, option)
            .map {
                it.data
            }
    }

    override fun getStudyList(category: String, sort: String): Single<StudyListResponse> {
        return remoteDataSource.getStudyList(category, sort)
            .map {
                it.data
            }
    }


    override fun getSearchStudyList(word: String): Single<StudyListResponse> {
        return remoteDataSource.getSearchStudyList(word)
            .map {
                it.data
            }
    }

    override fun leaveStudy(studyId: Int): Single<BaseResponse<Nothing>> {
        return remoteDataSource.leaveStudy(studyId)
    }

    override fun getStudyDetail(studyId: Int): Single<StudyDetailResponse> {
        return remoteDataSource.getStudyDetail(studyId)
            .map {
                it.data
            }
    }

    override fun getHotSearchKeyword(): Single<List<HotSearchKeywordResponse>> {
        return remoteDataSource.getHotSearchKeyword()
            .map {
                it.data
            }
    }

    override fun getMyStudyList(userId: Int): Single<MyStudyListResponse> {
        return remoteDataSource.getMyStudyList(userId)
            .map {
                it.data
            }
    }

    override fun deleteStudy(studyId: Int): Single<BaseResponse<Nothing>> {
        return remoteDataSource.deleteStudy(studyId)
    }

    override fun delegateStudyLeader(studyId: Int, newLeader: Int): Single<BaseResponse<Nothing>> {
        return remoteDataSource.delegateStudyLeader(studyId, newLeader)
    }

    override fun modifyStudy(studyId: Int, request: ModifyStudyRequest): Single<BaseResponse<Nothing>> {
        return remoteDataSource.modifyStudy(studyId, request)
    }

    override fun getStudyCategory(): Single<List<String>> {
        return remoteDataSource.getStudyCategory()
            .map {
                it.data
            }
    }
}