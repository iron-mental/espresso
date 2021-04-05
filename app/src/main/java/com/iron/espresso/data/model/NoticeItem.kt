package com.iron.espresso.data.model

import java.io.Serializable

data class NoticeItem(
    val id: Int,
    val title: String,
    val contents: String,
    val pinned: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Serializable