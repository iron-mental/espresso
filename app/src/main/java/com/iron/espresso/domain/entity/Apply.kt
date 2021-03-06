package com.iron.espresso.domain.entity

data class Apply(
    val id: Int,
    val userId: Int,
    val studyId: Int,
    val nickname: String,
    val image: String,
    val message: String,
    val title: String
)