package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Alert
import com.iron.espresso.domain.repo.AlertRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAlertList @Inject constructor(private val alertRepository: AlertRepository) {

    operator fun invoke(): Single<List<Alert>> {
        return alertRepository.getAlertList()
    }
}