package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.KakaoRepository
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class ModifyUserLocation @Inject constructor(
    private val userRepository: UserRepository,
    private val kakaoRepository: KakaoRepository
) {

    operator fun invoke(
        sido: String,
        sigungu: String,
    ): Completable {
        return kakaoRepository.getAddress(query = "$sido $sigungu")
            .flatMap { (latitude, longitude) ->
                userRepository.modifyUserLocation(
                    latitude = latitude,
                    longitude = longitude,
                    sido = sido,
                    sigungu = sigungu
                )
            }
            .ignoreElement()
    }
}