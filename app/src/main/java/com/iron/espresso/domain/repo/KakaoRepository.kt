package com.iron.espresso.domain.repo

import io.reactivex.Single

interface KakaoRepository {

    fun getAddress(query: String): Single<Pair<Double, Double>>
}



