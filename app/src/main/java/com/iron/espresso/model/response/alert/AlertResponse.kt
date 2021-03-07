package com.iron.espresso.model.response.alert


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Alert

data class AlertResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("study_id")
    val studyId: Int = -1,
    @SerializedName("study_title")
    val studyTitle: String?,
    @SerializedName("pushEvent")
    val pushEvent: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("confirm")
    val confirm: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String?
) {
    fun toAlert(): Alert =
        Alert(
            id,
            studyId,
            studyTitle.orEmpty(),
            pushEvent.orEmpty(),
            message.orEmpty(),
            confirm,
            createdAt.orEmpty()
        )
}