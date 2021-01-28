package com.iron.espresso.model.response.address


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("sido")
    val sido: String?,
    @SerializedName("sigungu")
    val siGungu: List<String>?
)