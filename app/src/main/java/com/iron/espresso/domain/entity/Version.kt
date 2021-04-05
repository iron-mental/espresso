package com.iron.espresso.domain.entity

data class Version(
    val latestVersion: String,
    val force: Int,
    val maintenance: Boolean?
)