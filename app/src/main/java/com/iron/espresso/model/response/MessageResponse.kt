package com.iron.espresso.model.response


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Message

data class MessageResponse(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("message")
    val message: String
) {
    fun toMessage(): Message =
        Message(result, message)
}