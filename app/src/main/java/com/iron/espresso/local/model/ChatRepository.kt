package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatRepository {
    fun getAll(): Flowable<List<Chat>>

    @WorkerThread
    suspend fun insert(chat: Chat)

    @WorkerThread
    suspend fun insertAll(chat: List<Chat>)

    @WorkerThread
    suspend fun delete(chat: Chat)
}

class ChatRepositoryImpl @Inject constructor(private val chatLocalDataSource: ChatLocalDataSource) : ChatRepository {
    override fun getAll(): Flowable<List<Chat>> =
        chatLocalDataSource.getAll()

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