package com.iron.espresso.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table")
    fun getAll(): Flow<List<Chat>>

    @Insert
    fun insert(chat: Chat)

    @Insert
    fun insertAll(chat: List<Chat>)

    @Delete
    fun delete(chat: Chat)
}