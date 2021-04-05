package com.iron.espresso.model.response.user

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.AccessToken

data class AccessTokenResponse(@SerializedName("access_token") val accessToken: String? = null) {
    fun toAccessToken(): AccessToken =
        AccessToken(accessToken.orEmpty())
}