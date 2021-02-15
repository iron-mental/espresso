package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.BadgeItem
import com.iron.espresso.data.model.MyStudyListItem

data class MyStudyListResponse(
    @SerializedName("badge")
    val badgeResponse: BadgeResponse?,
    @SerializedName("studyList")
    val myStudyResponse: ArrayList<MyStudyResponse>?
) {
    fun toMyStudyListItem() =
        MyStudyListItem(
            badge = badgeResponse?.toBadgeItem() ?: BadgeItem(),
            myStudyItem = myStudyResponse?.map {
                it.toMyStudyItem()
            }.orEmpty()
        )
}