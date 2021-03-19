package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(studyId: Int): Flowable<List<Chat>>

    fun getTimeStamp(studyId: Int): Long

    @WorkerThread
    suspend fun insert(chat: Chat)

    @WorkerThread
    suspend fun insertAll(chat: List<Chat>)

    @WorkerThread
    suspend fun delete(chat: Chat)
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) : ChatLocalDataSource {
    override fun getAll(studyId: Int): Flowable<List<Chat>> =
        chatDao.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Long =
        chatDao.getTimeStamp(studyId)

    @WorkerThread
    override suspend fun insert(chat: Chat) =
        chatDao.insert(chat)

    @WorkerThread
    override suspend fun insertAll(chat: List<Chat>) =
        chatDao.insertAll(chat)

    @WorkerThread
    override suspend fun delete(chat: Chat) =
        chatDao.delete(chat)

}