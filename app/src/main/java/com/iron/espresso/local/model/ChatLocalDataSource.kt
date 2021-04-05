package com.iron.espresso.local.model

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun getTimeStamp(studyId: Int): Single<Long>

    fun insert(chatEntity: ChatEntity): Completable

    fun insertAll(chatEntity: List<ChatEntity>): Completable

    fun deleteBookmark(): Completable

    fun updateNickname(userId: Int, nickname: String): Completable

    fun delete(studyId: Int): Completable

    fun deleteAll(studyId: Int): Completable
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) :
    ChatLocalDataSource {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatDao.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Single<Long> =
        chatDao.getTimeStamp(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatDao.insert(chatEntity)

    override fun insertAll(chatEntity: List<ChatEntity>): Completable =
        chatDao.insertAll(chatEntity)

    override fun deleteBookmark(): Completable =
        chatDao.deleteBookmark()

    override fun updateNickname(userId: Int, nickname: String): Completable =
        chatDao.updateNickname(userId, nickname)

    override fun delete(studyId: Int): Completable =
        chatDao.delete(studyId)

    override fun deleteAll(studyId: Int): Completable =
        chatDao.deleteAll(studyId)
}