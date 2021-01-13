package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.data.model.StudyItem

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
) {
    fun toStudyItem() =
        StudyItem(
            id = id ?: -1,
            title = title.orEmpty(),
            introduce = introduce.orEmpty(),
            image = image,
            sigungu = sigungu.orEmpty(),
            leaderImage = leaderImage,
            createdAt = createdAt.orEmpty(),
            distance = distance ?: -1.0,
            members = members ?: -1
        )
}