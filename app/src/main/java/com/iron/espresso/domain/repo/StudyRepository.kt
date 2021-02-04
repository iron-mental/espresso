package com.iron.espresso.domain.repo

import com.iron.espresso.model.response.study.HotSearchKeywordResponse
import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single

interface StudyRepository {
    fun getStudyPagingList(studyIds: List<Int>, option: String): Single<StudyListResponse>

    fun getStudyList(category: String, sort: String): Single<StudyListResponse>

    fun getSearchStudyList(word: String): Single<StudyListResponse>

    fun getHotSearchKeyword(): Single<List<HotSearchKeywordResponse>>
}