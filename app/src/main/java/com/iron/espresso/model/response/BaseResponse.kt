package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("result") val result: Boolean,
    @SerializedName("type") val type: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T?,
)