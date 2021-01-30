package com.iron.espresso.domain.entity

data class Project(
    val id: Int = -1,
    val title: String = "",
    val contents: String = "",
    val githubUrl: String = "",
    val appStoreUrl: String = "",
    val playStoreUrl: String = ""
)
