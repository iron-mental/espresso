package com.iron.espresso.local.model

import com.iron.espresso.domain.repo.ChatRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatLocalDataSource: ChatLocalDataSource) : ChatRepository {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Single<Long> =
        chatLocalDataSource.getTimeStamp(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.insert(chatEntity)

    override fun insertAll(chatEntity: List<ChatEntity>): Completable =
        chatLocalDataSource.insertAll(chatEntity)

    override fun delete(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.delete(chatEntity)
}