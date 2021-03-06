package com.iron.espresso.data.model

data class StudyItem(
    val id: Int,
    val title: String,
    val introduce: String,
    val image: String?,
    val sigungu: String,
    val leaderImage: String?,
    val createdAt: Long,
    val distance: Double,
    val memberCount: Int,
    val isMember: Boolean,
    val isPaging: Boolean
)