package com.iron.espresso.data.model

import java.io.Serializable

data class PlaceItem(
    val lat: Double,
    val lng: Double,
    val sido: String,
    val sigungu: String,
    val addressName: String,
    val placeName: String?,
    val locationDetail: String?
): Serializable