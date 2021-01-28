package com.iron.espresso.model.response.address


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("sido")
    val si: String?,
    @SerializedName("sigungu")
    val gunGu: List<String>?
)