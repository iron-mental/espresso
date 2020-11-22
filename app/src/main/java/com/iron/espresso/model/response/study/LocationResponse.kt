package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("address_name")
    val addressName: String?,
    @SerializedName("place_name")
    val placeName: String?,
    @SerializedName("location_detail")
    val locationDetail: String?
)