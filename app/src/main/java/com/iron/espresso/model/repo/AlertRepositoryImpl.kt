package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.Alert
import com.iron.espresso.domain.repo.AlertRepository
import com.iron.espresso.model.source.remote.AlertRemoteDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(private val alertRemoteDataSource: AlertRemoteDataSource) :
    AlertRepository {

    override fun getAlertList(): Single<List<Alert>> {
        return alertRemoteDataSource.getAlertList()
            .map { response ->
                if (!response.result) error(response.message.orEmpty())

                response.data?.map { it.toAlert() }
            }
    }

    override fun readAlert(alertId: Int): Single<Pair<Boolean, String>> {
        return alertRemoteDataSource.readAlert(alertId)
            .map {
                it.result to it.message.orEmpty()
            }
    }
}