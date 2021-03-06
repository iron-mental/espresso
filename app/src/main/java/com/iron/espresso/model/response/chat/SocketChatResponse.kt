package com.iron.espresso.model.response.chat

import com.google.gson.annotations.SerializedName
import com.iron.espresso.local.model.ChatEntity

data class SocketChatResponse(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("study_id")
    val studyId: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("date")
    val date: Long = -1L
) {
    fun toChatEntity(): ChatEntity =
        ChatEntity(
            uuid = uuid.orEmpty(),
            studyId = studyId,
            userId = userId,
            nickname = nickname.orEmpty(),
            message = message.orEmpty(),
            timeStamp = date
        )
}