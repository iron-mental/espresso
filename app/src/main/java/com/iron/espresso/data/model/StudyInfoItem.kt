package com.iron.espresso.data.model

data class StudyInfoItem(
    val participateItem: List<ParticipateItem> = listOf(),
    val id: Int = -1,
    val category: String = "",
    val title: String = "",
    val introduce: String = "",
    val image: String? = null,
    val progress: String = "",
    val studyTime: String = "",
    val snsNotion: String? = null,
    val snsEvernote: String? = null,
    val snsWeb: String? = null,
    val locationItem: LocationItem = LocationItem(),
    val authority: String = ""
)