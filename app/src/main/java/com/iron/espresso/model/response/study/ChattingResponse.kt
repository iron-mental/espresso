package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName
import com.iron.espresso.AuthHolder
import com.iron.espresso.presentation.home.mystudy.studydetail.chat.ChatItem

data class ChattingResponse(
    @SerializedName("chat")
    val chat: List<Chat>?,
    @SerializedName("user_list")
    val userList: List<ChatUser>?
) {
    fun toChatItem(): List<ChatItem> {
        val chatItem = mutableListOf<ChatItem>()
        chat?.forEach { chat ->
            userList?.forEach { chatUser ->
                if (chat.userId == chatUser.userId) {
                    chatItem.add(
                        ChatItem(
                            name = chatUser.nickname.orEmpty(),
                            message = chat.message.orEmpty(),
                            timeStamp = chat.date,
                            isMyChat = chat.userId == AuthHolder.requireId()
                        )
                    )
                }
            }
        }
        return chatItem
    }
}

data class Chat(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("study_id")
    val studyId: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("message")
    val message: String?,
    @SerializedName("date")
    val date: Long = -1
)

data class ChatUser(
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("nickname")
    val nickname: String?
)