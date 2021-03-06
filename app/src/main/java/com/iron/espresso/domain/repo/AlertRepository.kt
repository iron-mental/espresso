package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.Alert
import io.reactivex.rxjava3.core.Single

interface AlertRepository {
    fun getAlertList(): Single<List<Alert>>

    fun readAlert(alertId: Int): Single<Pair<Boolean, String>>
}