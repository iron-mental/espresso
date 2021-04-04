package com.iron.espresso.model.response.alert


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Alert
import com.iron.espresso.domain.entity.AlertType

data class AlertResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("study_id")
    val studyId: Int = -1,
    @SerializedName("study_title")
    val studyTitle: String?,
    @SerializedName("pushEvent")
    val alertType: AlertType?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("confirm")
    val confirm: Boolean = false,
    @SerializedName("created_at")
    val createdAt: Long = -1
) {
    fun toAlert(): Alert =
        Alert(
            id,
            studyId,
            studyTitle.orEmpty(),
            alertType,
            message.orEmpty(),
            confirm,
            createdAt
        )
}