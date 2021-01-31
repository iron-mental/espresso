package com.iron.espresso.domain.repo

import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single

interface StudyRepository {
    fun getStudyPagingList(sort: String, studyIds: List<Int>): Single<StudyListResponse>

    fun getStudyList(category: String, sort: String): Single<StudyListResponse>
}