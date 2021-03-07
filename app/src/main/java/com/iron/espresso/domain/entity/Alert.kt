package com.iron.espresso.domain.entity

data class Alert(
    val id: Int = -1,
    val studyId: Int = -1,
    val studyTitle: String,
    val alertType: AlertType?,
    val message: String,
    val confirm: Boolean,
    val createdAt: String
)