package com.iron.espresso.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class Chat(
    @PrimaryKey @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "timeStamp") val timeStamp: Long?,
    @ColumnInfo(name = "isMyChat") val isMyChat: Boolean?
)