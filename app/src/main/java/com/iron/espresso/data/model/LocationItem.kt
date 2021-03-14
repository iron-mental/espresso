package com.iron.espresso.data.model

import java.io.Serializable

data class LocationItem(
    val latitude: String = "",
    val longitude: String = "",
    val addressName: String = "",
    val placeName: String? = null,
    val locationDetail: String? = null
) : Serializable