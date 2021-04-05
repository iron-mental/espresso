package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.AlertRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ReadAlert @Inject constructor(private val alertRepository: AlertRepository) {

    operator fun invoke(alertId: Int): Single<Pair<Boolean, String>> {
        return alertRepository.readAlert(alertId)
    }
}