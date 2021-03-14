package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.MyStudyItem

data class MyStudyResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("sigungu")
    val sigungu: String?,
    @SerializedName("image")
    val image: String?
) {
    fun toMyStudyItem() =
        MyStudyItem(
            id = id ?: -1,
            title = title.orEmpty(),
            sigungu = sigungu.orEmpty(),
            image = image.orEmpty()
        )
}