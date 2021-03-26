package com.iron.espresso.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iron.espresso.domain.entity.Chat
import com.iron.espresso.domain.entity.ChatUser

@Entity(tableName = "chat_table")
data class ChatEntity(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "study_id") val studyId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long
) {
    companion object {
        fun of(chatList: List<Chat>, userList: List<ChatUser>): List<ChatEntity> {

            val list = mutableListOf<ChatEntity>()

            chatList.forEach { chat ->
                if (chat.userId == 0) {
                    list.add(
                        ChatEntity(
                            uuid = chat.uuid,
                            studyId = chat.studyId,
                            userId = chat.userId,
                            nickname = "",
                            message = chat.message,
                            timeStamp = chat.date
                        )
                    )
                } else {
                    userList.forEach { chatUser ->
                        if (chat.userId == chatUser.userId) {
                            list.add(
                                ChatEntity(
                                    uuid = chat.uuid,
                                    studyId = chat.studyId,
                                    userId = chat.userId,
                                    nickname = chatUser.nickname,
                                    message = chat.message,
                                    timeStamp = chat.date
                                )
                            )
                        }
                    }
                }
            }
            return list
        }
    }
}