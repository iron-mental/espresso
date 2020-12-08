package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("result") val result: Boolean = false,
    @SerializedName("type") val type: String? = null,
    @SerializedName("label") val label: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: T? = null,
)

typealias ErrorResponse = BaseResponse<*>

enum class Label(val value: String) {
    @SerializedName("jwt_expired")
    JWT_EXPIRED("jwt_expired");
}