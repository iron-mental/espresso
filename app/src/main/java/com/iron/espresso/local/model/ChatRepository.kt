package com.iron.espresso.local.model

import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import javax.inject.Inject

interface ChatRepository {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun getTimeStamp(studyId: Int): Long

    @WorkerThread
    suspend fun insert(chatEntity: ChatEntity)

    @WorkerThread
    suspend fun insertAll(chatEntity: List<ChatEntity>)

    @WorkerThread
    suspend fun delete(chatEntity: ChatEntity)
}

class ChatRepositoryImpl @Inject constructor(private val chatLocalDataSource: ChatLocalDataSource) : ChatRepository {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Long =
        chatLocalDataSource.getTimeStamp(studyId)

    @WorkerThread
    override suspend fun insert(chatEntity: ChatEntity) =
        chatLocalDataSource.insert(chatEntity)

    @WorkerThread
    override suspend fun insertAll(chatEntity: List<ChatEntity>) =
        chatLocalDataSource.insertAll(chatEntity)

    @WorkerThread
    override suspend fun delete(chatEntity: ChatEntity) =
        chatLocalDataSource.delete(chatEntity)
}