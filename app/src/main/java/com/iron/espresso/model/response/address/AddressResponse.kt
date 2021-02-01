package com.iron.espresso.model.response.address


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Address

data class AddressResponse(
    @SerializedName("sido")
    val sido: String?,
    @SerializedName("sigungu")
    val siGungu: List<String>?
) {
    fun toAddress(): Address =
        Address(sido.orEmpty(), siGungu.orEmpty())

}