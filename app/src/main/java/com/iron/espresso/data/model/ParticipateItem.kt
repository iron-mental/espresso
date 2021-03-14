package com.iron.espresso.data.model

import java.io.Serializable

data class ParticipateItem(
    val id: Int,
    val userId: Int,
    val nickname: String,
    val image: String?,
    val leader: Boolean
) : Serializable