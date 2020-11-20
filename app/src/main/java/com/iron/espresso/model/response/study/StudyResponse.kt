package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName

data class StudyResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("introduce")
    val introduce: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("sigungu")
    val sigungu: String?,
    @SerializedName("leader_image")
    val leaderImage: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("distance")
    val distance: Double?,
    @SerializedName("members")
    val members: Int?
)