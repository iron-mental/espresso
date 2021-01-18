package com.iron.espresso.data.model

data class StudyItem(
    val id: Int,
    val title: String,
    val introduce: String,
    val image: String?,
    val sigungu: String,
    val leaderImage: String?,
    val createdAt: String,
    val distance: Double,
    val members: Int,
    val isMember: Boolean
)