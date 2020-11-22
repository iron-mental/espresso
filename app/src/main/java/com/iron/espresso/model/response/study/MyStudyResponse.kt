package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName

data class MyStudyResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("sigungu")
    val sigungu: String?,
    @SerializedName("image")
    val image: String?
)