package com.iron.espresso.model.response.user


import com.google.gson.annotations.SerializedName

data class UserAuthResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("id")
    val id: Int?
)