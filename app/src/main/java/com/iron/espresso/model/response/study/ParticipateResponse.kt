package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.ParticipateItem

data class ParticipateResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("leader")
    val leader: Boolean?
) {
    fun toParticipateItem() = ParticipateItem(id, userId, nickname, image, leader)
}