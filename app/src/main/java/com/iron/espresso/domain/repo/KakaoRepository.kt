package com.iron.espresso.domain.repo

import io.reactivex.rxjava3.core.Single

interface KakaoRepository {

    fun getAddress(query: String): Single<Pair<Double, Double>>
}



