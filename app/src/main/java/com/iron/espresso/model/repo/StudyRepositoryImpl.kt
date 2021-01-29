package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.model.response.study.StudyListResponse
import com.iron.espresso.model.source.remote.StudyRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class StudyRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudyRemoteDataSource
) : StudyRepository {

    override fun getStudyPagingList(sort: String, studyIds: String): Single<StudyListResponse> {
        return remoteDataSource.getStudyPagingList(sort, studyIds)
            .map {
                it.data
            }
    }
}