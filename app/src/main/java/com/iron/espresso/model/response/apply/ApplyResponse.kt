package com.iron.espresso.model.response.apply

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Apply

data class ApplyResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("message")
    val message: String?
) {
    fun toApply(): Apply =
        Apply(
            id = id,
            userId = userId,
            nickname = nickname.orEmpty(),
            image = image.orEmpty(),
            message = message.orEmpty()
        )
}