package com.iron.espresso.model.response.kakao


import com.google.gson.annotations.SerializedName

data class KakaoAddressListResponse(
    @SerializedName("documents")
    val kakaoAddressRespons: List<KakaoAddressResponse>?,
    @SerializedName("meta")
    val meta: Meta?
)