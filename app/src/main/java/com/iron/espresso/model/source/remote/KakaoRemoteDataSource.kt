package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.kakao.KakaoAddressListResponse
import io.reactivex.Single
import javax.inject.Inject

interface KakaoRemoteDataSource {
    fun getAddress(query: String): Single<KakaoAddressListResponse>
}

class KakaoRemoteDataSourceImpl @Inject constructor(private val kakaoApi: KakaoApi) :
    KakaoRemoteDataSource {

    override fun getAddress(query: String): Single<KakaoAddressListResponse> {
        return kakaoApi.getAddress(query = query)
    }
}