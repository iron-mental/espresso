package com.iron.espresso.model.response.address


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("si")
    val si: String?,
    @SerializedName("gunGu")
    val gunGu: List<String>?
)