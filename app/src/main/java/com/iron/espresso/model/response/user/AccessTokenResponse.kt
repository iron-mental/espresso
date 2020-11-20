package com.iron.espresso.model.response.user

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(@SerializedName("access_token") val accessToken: String)