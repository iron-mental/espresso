package com.iron.espresso.domain.entity

data class Project(
    val id: Int,
    val title: String,
    val contents: String,
    val snsGithub: String,
    val snsAppstore: String,
    val snsPlaystore: String,
    val createdAt: String
)