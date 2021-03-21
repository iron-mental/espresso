package com.iron.espresso.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table WHERE study_id IN (:studyId)")
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    @Query("SELECT max(time_stamp) FROM chat_table WHERE study_id IN (:studyId)")
    fun getTimeStamp(studyId: Int): Single<Long>

    @Insert
    fun insert(chatEntity: ChatEntity): Completable

    @Insert
    fun insertAll(chatEntity: List<ChatEntity>): Completable

    @Delete
    fun delete(chatEntity: ChatEntity): Completable
}