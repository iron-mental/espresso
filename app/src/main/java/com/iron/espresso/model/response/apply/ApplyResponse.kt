package com.iron.espresso.model.response.apply

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Apply

data class ApplyResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("image")
    val image: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("title")
    val title: String?
) {
    fun toApply(): Apply =
        Apply(
            id = id,
            userId = userId,
            image = image.orEmpty(),
            message = message.orEmpty(),
            title = title.orEmpty()
        )
}