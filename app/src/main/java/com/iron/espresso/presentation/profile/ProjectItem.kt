package com.iron.espresso.presentation.profile

data class ProjectItem(
    val id: Int,
    val title: String,
    val contents: String,
    val githubUrl: String,
    val appStoreUrl: String,
    val playStoreUrl: String
)