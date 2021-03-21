package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import com.iron.espresso.AuthHolder
import com.iron.espresso.domain.entity.Chat
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.local.model.ChatEntity

data class ChatItem(
    val uuid: String,
    val studyId: Int,
    val userId: Int,
    val name: String,
    val message: String,
    val timeStamp: Long,
    val isMyChat: Boolean,
) {
    companion object {
        fun of(chatList: List<Chat>, userList: List<ChatUser>): List<ChatItem> {
            val chatItem = mutableListOf<ChatItem>()
            chatList.forEach { chat ->
                if (chat.userId == 0) {
                    chatItem.add(
                        ChatItem(
                            uuid = "",
                            studyId = chat.studyId,
                            userId = chat.userId,
                            name = "__SYSTEM__",
                            message = chat.message,
                            timeStamp = chat.date,
                            isMyChat = false
                        )
                    )
                } else {
                    userList.forEach { chatUser ->
                        if (chat.userId == chatUser.userId) {
                            chatItem.add(
                                ChatItem(
                                    uuid = chat.uuid,
                                    studyId = chat.studyId,
                                    userId = chat.userId,
                                    name = chatUser.nickname,
                                    message = chat.message,
                                    timeStamp = chat.date,
                                    isMyChat = chat.userId == AuthHolder.requireId()
                                )
                            )
                        }
                    }
                }
            }
            return chatItem
        }

        fun of(chatEntityList: List<ChatEntity>): List<ChatItem> {
            return chatEntityList.map {
                ChatItem(
                    uuid = it.uuid,
                    studyId = it.studyId,
                    userId = it.userId,
                    name = it.nickname,
                    message = it.message,
                    timeStamp = it.timeStamp,
                    isMyChat = it.isMyChat
                )
            }
        }
    }
}

