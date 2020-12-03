package com.iron.espresso.model.response.notice


import com.google.gson.annotations.SerializedName

data class NoticeDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("study_id")
    val studyId: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("pinned")
    val pinned: Boolean?,
    @SerializedName("leader_id")
    val leaderId: Int?,
    @SerializedName("leader_image")
    val leaderImage: String?,
    @SerializedName("leader_nickname")
    val leaderNickname: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)