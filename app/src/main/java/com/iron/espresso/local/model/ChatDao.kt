package com.iron.espresso.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table WHERE study_id IN (:studyId)")
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    @Query("SELECT max(time_stamp) FROM chat_table WHERE study_id IN (:studyId)")
    fun getTimeStamp(studyId: Int): Long

    @Insert
    suspend fun insert(chatEntity: ChatEntity)

    @Insert
    suspend fun insertAll(chatEntity: List<ChatEntity>)

    @Delete
    suspend fun delete(chatEntity: ChatEntity)
}