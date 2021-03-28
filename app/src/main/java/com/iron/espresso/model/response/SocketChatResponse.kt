package com.iron.espresso.model.response


import com.google.gson.annotations.SerializedName

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
)