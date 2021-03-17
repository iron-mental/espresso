package com.iron.espresso.local.model

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatRepository {
    fun getAll(): Flow<List<Chat>>

    fun insert(chat: Chat)

    fun insertAll(chat: List<Chat>)

    fun delete(chat: Chat)
}

class ChatRepositoryImpl @Inject constructor(private val chatLocalDataSource: ChatLocalDataSource) : ChatRepository {
    override fun getAll(): Flow<List<Chat>> =
        chatLocalDataSource.getAll()

    override fun insert(chat: Chat) =
        chatLocalDataSource.insert(chat)

    override fun insertAll(chat: List<Chat>) =
        chatLocalDataSource.insertAll(chat)

    override fun delete(chat: Chat) =
        chatLocalDataSource.delete(chat)
}