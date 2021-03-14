package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.Apply
import com.iron.espresso.domain.entity.ApplyDetail
import com.iron.espresso.domain.repo.ApplyRepository
import com.iron.espresso.model.source.remote.ApplyRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class ApplyRepositoryImpl @Inject constructor(private val remoteDataSource: ApplyRemoteDataSource) :
    ApplyRepository {
    override fun registerApply(
        studyId: Int,
        message: String
    ): Single<Pair<Boolean, String>> {
        return remoteDataSource.registerApply(studyId, message)
            .map {
                it.result to it.message.orEmpty()
            }
    }

    override fun getApplyByOwner(studyId: Int, applyId: Int): Single<ApplyDetail> {
        return remoteDataSource.getApplyByOwner(studyId, applyId)
            .map { response ->
                if (!response.result) error(response.message.orEmpty())

                response.data?.toApplyDetail()
            }
    }

    override fun getApplyByApplier(studyId: Int, userId: Int): Single<ApplyDetail> {
        return remoteDataSource.getApplyByApplier(studyId, userId)
            .map { response ->
                if (!response.result) error(response.message.orEmpty())

                response.data?.toApplyDetail()
            }
    }

    override fun getApplyList(studyId: Int): Single<List<Apply>> {
        return remoteDataSource.getApplyList(studyId)
            .map { response ->
                if (!response.result) error(response.message.orEmpty())

                response.data?.map { it.toApply() }
            }
    }

    override fun getMyApplyList(): Single<List<Apply>> {
        return remoteDataSource.getMyApplyList()
            .map { response ->
                if (!response.result) error(response.message.orEmpty())

                response.data?.map { it.toApply() }
            }
    }

    override fun modifyApply(
        studyId: Int,
        applyId: Int,
        message: String
    ): Single<Pair<Boolean, String>> {
        return remoteDataSource.modifyApply(studyId, applyId, message)
            .map {
                it.result to it.message.orEmpty()
            }
    }

    override fun deleteApply(studyId: Int, applyId: Int): Single<Pair<Boolean, String>> {
        return remoteDataSource.deleteApply(studyId, applyId)
            .map {
                it.result to it.message.orEmpty()
            }
    }

    override fun handleApply(studyId: Int, applyId: Int, allow: Boolean): Single<Pair<Boolean, String>> {
        return remoteDataSource.handleApply(studyId, applyId, allow)
            .map {
                it.result to it.message.orEmpty()
            }
    }
}