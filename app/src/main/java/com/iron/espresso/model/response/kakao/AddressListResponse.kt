package com.iron.espresso.model.response.kakao


import com.google.gson.annotations.SerializedName

data class AddressListResponse(
    @SerializedName("documents")
    val addressResponses: List<AddressResponse>?,
    @SerializedName("meta")
    val meta: Meta?
)