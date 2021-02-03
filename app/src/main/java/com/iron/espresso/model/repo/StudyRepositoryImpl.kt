package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.StudyListResponse
import com.iron.espresso.model.source.remote.StudyRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class StudyRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudyRemoteDataSource
) : StudyRepository {

    override fun getStudyPagingList(sort: String, studyIds: List<Int>): Single<StudyListResponse> {
        return remoteDataSource.getStudyPagingList(sort, studyIds)
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
}