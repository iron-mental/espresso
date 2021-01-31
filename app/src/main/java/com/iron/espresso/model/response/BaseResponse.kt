package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("result") val result: Boolean = false,
    @SerializedName("type") val type: String? = null,
    @SerializedName("code") val code: ErrorCode? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: T? = null,
)

typealias ErrorResponse = BaseResponse<*>

enum class ErrorCode {
    @SerializedName("101")
    JWT_EXPIRED;
}