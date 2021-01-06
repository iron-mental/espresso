package com.iron.espresso.data.model

data class StudyDetailItem(
    val participateResponse: List<ParticipateItem>?,
    val id: Int?,
    val category: String?,
    val title: String?,
    val introduce: String?,
    val image: String?,
    val progress: String?,
    val studyTime: String?,
    val snsNotion: String?,
    val snsEvernote: String?,
    val snsWeb: String?,
    val locationResponse: LocationItem?,
    val authority: String?
)