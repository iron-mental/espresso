package com.iron.espresso.local.model

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatLocalDataSource {
    fun getAll(): Flow<List<Chat>>

    fun insert(chat: Chat)

    fun insertAll(chat: List<Chat>)

    fun delete(chat: Chat)
}

class ChatLocalDataSourceImpl @Inject constructor(private val chatDao: ChatDao) : ChatLocalDataSource {
    override fun getAll(): Flow<List<Chat>> =
        chatDao.getAll()

    override fun insert(chat: Chat) =
        chatDao.insert(chat)

    override fun insertAll(chat: List<Chat>) =
        chatDao.insertAll(chat)

    override fun delete(chat: Chat) =
        chatDao.delete(chat)

}