package com.iron.espresso.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table")
    fun getAll(): Flowable<List<Chat>>

    @Insert
    suspend fun insert(chat: Chat)

    @Insert
    suspend fun insertAll(chat: List<Chat>)

    @Delete
    suspend fun delete(chat: Chat)
}