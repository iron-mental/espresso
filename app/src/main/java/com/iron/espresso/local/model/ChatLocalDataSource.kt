package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun getTimeStamp(studyId: Int): Long

    @WorkerThread
    suspend fun insert(chatEntity: ChatEntity)

    @WorkerThread
    suspend fun insertAll(chatEntity: List<ChatEntity>)

    @WorkerThread
    suspend fun delete(chatEntity: ChatEntity)
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) : ChatLocalDataSource {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatDao.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Long =
        chatDao.getTimeStamp(studyId)

    @WorkerThread
    override suspend fun insert(chatEntity: ChatEntity) =
        chatDao.insert(chatEntity)

    @WorkerThread
    override suspend fun insertAll(chatEntity: List<ChatEntity>) =
        chatDao.insertAll(chatEntity)

    @WorkerThread
    override suspend fun delete(chatEntity: ChatEntity) =
        chatDao.delete(chatEntity)

}