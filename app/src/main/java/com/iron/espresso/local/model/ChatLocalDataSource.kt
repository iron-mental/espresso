package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(): Flowable<List<Chat>>

    fun getTimeStamp(): Long

    @WorkerThread
    suspend fun insert(chat: Chat)

    @WorkerThread
    suspend fun insertAll(chat: List<Chat>)

    @WorkerThread
    suspend fun delete(chat: Chat)
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) : ChatLocalDataSource {
    override fun getAll(): Flowable<List<Chat>> =
        chatDao.getAll()

    override fun getTimeStamp(): Long =
        chatDao.getTimeStamp()

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