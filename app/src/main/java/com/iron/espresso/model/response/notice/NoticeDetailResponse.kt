package com.iron.espresso.model.response.notice


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.NoticeDetailItem

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
    val updatedAt: Long = -1L
) {
    fun toNoticeDetailItem() = NoticeDetailItem(
        id = id ?: -1,
        studyId = studyId ?: -1,
        title = title.orEmpty(),
        contents = contents.orEmpty(),
        pinned = pinned ?: false,
        leaderId = leaderId ?: -1,
        leaderImage = leaderImage,
        leaderNickname = leaderNickname.orEmpty(),
        updatedAt = updatedAt
    )
}