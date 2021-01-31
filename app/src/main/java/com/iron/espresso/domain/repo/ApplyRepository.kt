package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.Apply
import com.iron.espresso.domain.entity.ApplyDetail
import io.reactivex.Single

interface ApplyRepository {
    fun registerApply(
        studyId: Int,
        message: String
    ): Single<Pair<Boolean, String>>

    fun getApplyByOwner(
        studyId: Int,
        applyId: Int
    ): Single<ApplyDetail>

    fun getApplyByApplier(
        studyId: Int,
        userId: Int
    ): Single<ApplyDetail>

    fun getApplyList(studyId: Int): Single<List<Apply>>

    fun getMyApplyList(): Single<List<Apply>>

    fun modifyApply(
        studyId: Int,
        applyId: Int,
        message: String
    ): Single<Pair<Boolean, String>>

    fun deleteApply(
        studyId: Int,
        applyId: Int
    ): Single<Pair<Boolean, String>>
}