package com.iron.espresso.data.model

import java.io.File

data class ModifyStudyItem(
    val category: String,
    val title: String,
    val introduce: String,
    val progress: String,
    val studyTime: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val sido: String = "",
    val sigungu: String = "",
    val addressName: String = "",
    val placeName: String = "",
    val locationDetail: String = "",
    val snsNotion: String = "",
    val snsEverNote: String = "",
    val snsWeb: String = "",
    val image: File? = null
)