package com.iron.espresso.local.model

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun getTimeStamp(studyId: Int): Single<Long>

    fun insert(chatEntity: ChatEntity): Completable

    fun insertAll(chatEntity: List<ChatEntity>): Completable

    fun delete(chatEntity: ChatEntity): Completable

    fun updateNickname(userId: Int, nickname: String): Completable
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) : ChatLocalDataSource {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatDao.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Single<Long> =
        chatDao.getTimeStamp(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatDao.insert(chatEntity)

    override fun insertAll(chatEntity: List<ChatEntity>): Completable =
        chatDao.insertAll(chatEntity)

    override fun delete(chatEntity: ChatEntity): Completable =
        chatDao.delete(chatEntity)

    override fun updateNickname(userId: Int, nickname: String): Completable =
        chatDao.updateNickname(userId, nickname)
}