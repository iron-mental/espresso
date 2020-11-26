package com.iron.espresso.data.model

import java.io.Serializable

data class LocalItem(
    val lat: Double,
    val lng: Double,
    val sido: String,
    val sigungu: String,
    val addressName: String,
    val placeName: String = "",
    var locationDetail: String = ""
): Serializable