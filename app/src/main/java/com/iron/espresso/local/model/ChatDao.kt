package com.iron.espresso.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table WHERE study_id IN (:studyId)")
    fun getAll(studyId: Int): Flowable<List<Chat>>

    @Query("SELECT max(time_stamp) FROM chat_table WHERE study_id IN (:studyId)")
    fun getTimeStamp(studyId: Int): Long

    @Insert
    suspend fun insert(chat: Chat)

    @Insert
    suspend fun insertAll(chat: List<Chat>)

    @Delete
    suspend fun delete(chat: Chat)
}