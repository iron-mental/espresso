package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName
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
    @SerializedName("member_count")
    val memberCount: Int?,
    @SerializedName("is_member")
    val isMember: Boolean?,
    @SerializedName("is_paging")
    val isPaging: Boolean?
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
            memberCount = memberCount ?: -1,
            isMember = isMember ?: false,
            isPaging = isPaging ?: false
        )
}