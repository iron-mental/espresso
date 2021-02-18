package com.iron.espresso.data.model

import java.io.File

data class ModifyStudyItem(
    val category: String,
    val title: String,
    val introduce: String,
    val progress: String,
    val studyTime: String,
    val localItem: LocalItem?,
    val snsNotion: String,
    val snsEverNote: String,
    val snsWeb: String,
    val image: File? = null
)