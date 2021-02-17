package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.BadgeItem

data class BadgeResponse(
    @SerializedName("alert")
    val alert: Int?,
    @SerializedName("total")
    val total: Int?
) {
    fun toBadgeItem() =
        BadgeItem(
            alert = alert ?: -1,
            total = total ?: -1
        )
}