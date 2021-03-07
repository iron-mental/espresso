package com.iron.espresso.presentation.home.alert

import com.iron.espresso.domain.entity.Alert
import com.iron.espresso.domain.entity.AlertType
import java.util.*

data class AlertItem(
    val id: Int = -1,
    val studyId: Int = -1,
    val studyTitle: String,
    val alertType: AlertType?,
    val message: String,
    val confirm: Boolean,
    val pastDate: String
) {
    companion object {
        fun of(alert: Alert): AlertItem {
            val date = Calendar.getInstance().apply {
                val day = alert.createdAt.split(" ")[0].split("-")
                val time = alert.createdAt.split(" ")[1].split(":")
                set(
                    day[0].toInt(),
                    day[1].toInt() - 1,
                    day[2].toInt(),
                    time[0].toInt(),
                    time[1].toInt(),
                    time[2].toInt()
                )
            }

            val now = Calendar.getInstance()
            val diffMilliSec = now.timeInMillis - date.timeInMillis
            val pastDate = if (diffMilliSec < 3600000 * 24) {
                when {
                    diffMilliSec < 60000 -> {
                        "${diffMilliSec / 1000}초 전"
                    }
                    diffMilliSec < 3600000 -> {
                        "${diffMilliSec / 60000}분 전"
                    }
                    else -> {
                        "${diffMilliSec / 3600000}시간 전"
                    }
                }
            } else {
                "${getPastDays(now, date)}일 전"
            }

            return AlertItem(
                id = alert.id,
                studyId = alert.studyId,
                studyTitle = alert.studyTitle,
                alertType = alert.alertType,
                message = alert.message,
                confirm = alert.confirm,
                pastDate = pastDate
            )
        }

        private fun getPastDays(now: Calendar, date: Calendar): Int {
            var days = 0
            while (now.timeInMillis > date.timeInMillis) {
                now.add(Calendar.DAY_OF_MONTH, -1)
                days++
            }

            return days
        }
    }
}
