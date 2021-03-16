package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName

data class ChattingResponse(
    @SerializedName("chat")
    val chat: List<Chat>?,
    @SerializedName("user_list")
    val userList: List<ChatUser>?
)

data class Chat(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("study_id")
    val studyId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("date")
    val date: Long?
)

data class ChatUser(
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("nickname")
    val nickname: String?
)