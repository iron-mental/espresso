package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatRepository {
    fun getAll(studyId: Int): Flowable<List<Chat>>

    fun getTimeStamp(studyId: Int): Long

    @WorkerThread
    suspend fun insert(chat: Chat)

    @WorkerThread
    suspend fun insertAll(chat: List<Chat>)

    @WorkerThread
    suspend fun delete(chat: Chat)
}

class ChatRepositoryImpl @Inject constructor(private val chatLocalDataSource: ChatLocalDataSource) : ChatRepository {
    override fun getAll(studyId: Int): Flowable<List<Chat>> =
        chatLocalDataSource.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Long =
        chatLocalDataSource.getTimeStamp(studyId)

    @WorkerThread
    override suspend fun insert(chat: Chat) =
        chatLocalDataSource.insert(chat)

    @WorkerThread
    override suspend fun insertAll(chat: List<Chat>) =
        chatLocalDataSource.insertAll(chat)

    @WorkerThread
    override suspend fun delete(chat: Chat) =
        chatLocalDataSource.delete(chat)
}