package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.KakaoRepository
import com.iron.espresso.model.source.remote.KakaoRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val remoteDataSource: KakaoRemoteDataSource) :
    KakaoRepository {

    override fun getAddress(query: String): Single<Pair<Double, Double>> {
        return remoteDataSource.getAddress(query)
            .map {
                val address =
                    it.kakaoAddressRespons?.firstOrNull() ?: error("Address Response null")
                val latitude =
                    address.y?.toDoubleOrNull() ?: error("Address Response latitude null")
                val longitude =
                    address.x?.toDoubleOrNull() ?: error("Address Response longitude null")

                latitude to longitude
            }

    }
}