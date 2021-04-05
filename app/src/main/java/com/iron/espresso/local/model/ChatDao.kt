package com.iron.espresso.local.model

import androidx.room.*
import com.iron.espresso.AuthHolder
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_table WHERE study_id = (:studyId) AND connect_id = (:connectId)")
    fun getAll(studyId: Int, connectId: Int = AuthHolder.requireId()): Flowable<List<ChatEntity>>

    @Query("SELECT max(time_stamp) FROM chat_table WHERE study_id = (:studyId) AND connect_id = (:connectId)")
    fun getTimeStamp(studyId: Int, connectId: Int = AuthHolder.requireId()): Single<Long>

    @Insert
    fun insert(chatEntity: ChatEntity): Completable

    @Insert
    fun insertAll(chatEntity: List<ChatEntity>): Completable

    @Query("UPDATE chat_table SET nickname = (:nickname) WHERE user_id = (:userId) AND connect_id = (:connectId)")
    fun updateNickname(userId: Int, nickname: String, connectId: Int = AuthHolder.requireId()): Completable

    @Query("DELETE FROM chat_table WHERE uuid = (:uuid)")
    fun deleteBookmark(uuid: String = "bookmark"): Completable

    @Query("DELETE FROM chat_table WHERE study_id = (:studyId) AND connect_id = (:connectId)")
    fun delete(studyId: Int, connectId: Int = AuthHolder.requireId()): Completable

    @Query("DELETE FROM chat_table WHERE study_id = (:studyId)")
    fun deleteAll(studyId: Int): Completable
}