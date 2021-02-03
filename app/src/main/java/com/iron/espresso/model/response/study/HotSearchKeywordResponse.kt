package com.iron.espresso.model.response.study

import com.google.gson.annotations.SerializedName
import com.iron.espresso.presentation.home.study.HotKeywordItem

data class HotSearchKeywordResponse(
    @SerializedName("word")
    val word: String?
) {
    fun toHotKeywordItem() =
        HotKeywordItem(
            word = word.orEmpty()
        )
}