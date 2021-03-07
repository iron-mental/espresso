package com.iron.espresso.presentation.home.alert

import com.iron.espresso.domain.entity.Alert

data class AlertItem(
    val id: Int = -1,
    val studyId: Int = -1,
    val studyTitle: String,
    val pushEvent: String,
    val message: String,
    val confirm: Boolean,
    val createdAt: String
) {
    companion object {
        fun of(alert: Alert): AlertItem {
            return AlertItem(
                id = alert.id,
                studyId = alert.studyId,
                studyTitle = alert.studyTitle,
                pushEvent = alert.pushEvent,
                message = alert.message,
                confirm = alert.confirm,
                createdAt = alert.createdAt
            )
        }
    }
}
