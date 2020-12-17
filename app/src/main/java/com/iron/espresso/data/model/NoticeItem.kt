package com.iron.espresso.data.model

import java.io.Serializable

data class NoticeItem(
    val title: String,
    val contents: String,
    val pinned: Boolean
): Serializable