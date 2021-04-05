package com.iron.espresso.model.response.notice


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.NoticeItem

data class NoticeResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("pinned")
    val pinned: Boolean?,
    @SerializedName("created_at")
    val createdAt: Long = -1,
    @SerializedName("updated_at")
    val updatedAt: Long = -1
) {
    fun toNoticeItem() = NoticeItem(
        id = id ?: -1,
        title = title.orEmpty(),
        contents = contents.orEmpty(),
        pinned = pinned ?: false,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}