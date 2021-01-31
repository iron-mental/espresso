package com.iron.espresso.data.model

import java.io.Serializable

data class NoticeDetailItem(
    val id: Int,
    val studyId: Int,
    val title: String,
    val contents: String,
    val pinned: Boolean,
    val leaderId: Int,
    val leaderImage: String?,
    val leaderNickname: String,
    val updatedAt: String
) : Serializable