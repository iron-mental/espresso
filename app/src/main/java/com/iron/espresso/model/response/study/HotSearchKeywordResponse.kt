package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName

data class HotSearchKeywordResponse(
    @SerializedName("word")
    val word: String
)