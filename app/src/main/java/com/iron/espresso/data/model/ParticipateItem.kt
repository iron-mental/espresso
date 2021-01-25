package com.iron.espresso.data.model

data class ParticipateItem(
    val id: Int,
    val userId: Int,
    val nickname: String,
    val image: String?,
    val leader: Boolean
)