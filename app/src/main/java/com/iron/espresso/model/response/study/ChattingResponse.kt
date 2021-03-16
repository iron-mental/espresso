package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Chat
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.entity.Chatting

data class ChattingResponse(
    @SerializedName("chat")
    val chatResponseList: List<ChatResponse>?,
    @SerializedName("user_list")
    val chatUserResponseList: List<ChatUserResponse>?
) {
    fun toChatting(): Chatting =
        Chatting(
            chatList = chatResponseList?.map {
                it.toChat()
            }.orEmpty(),
            chatUserList = chatUserResponseList?.map {
                it.toChatUser()
            }.orEmpty()
        )

}

data class ChatResponse(
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
) {
    fun toChat(): Chat =
        Chat(
            uuid = uuid.orEmpty(),
            studyId = studyId,
            userId = userId,
            message = message.orEmpty(),
            date = date
        )
}

data class ChatUserResponse(
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("nickname")
    val nickname: String?
) {
    fun toChatUser(): ChatUser =
        ChatUser(
            userId = userId,
            nickname = nickname.orEmpty()
        )
}