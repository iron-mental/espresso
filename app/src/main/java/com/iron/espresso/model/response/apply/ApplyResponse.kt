package com.iron.espresso.model.response.apply


import com.google.gson.annotations.SerializedName

data class ApplyResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("message")
    val message: String?
)