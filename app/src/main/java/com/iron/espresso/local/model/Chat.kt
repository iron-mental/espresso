package com.iron.espresso.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class Chat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "study_id") val studyId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long,
    @ColumnInfo(name = "isMyChat") val isMyChat: Boolean
)