package com.iron.espresso.data.model

data class LocationItem(
    val latitude: String = "",
    val longitude: String = "",
    val addressName: String = "",
    val placeName: String? = null,
    val locationDetail: String? = null
)