package com.iron.espresso.model.response.user


import com.google.gson.annotations.SerializedName

data class CheckEmailResponse(
    @SerializedName("duplicate")
    val duplicate: Boolean = false
)