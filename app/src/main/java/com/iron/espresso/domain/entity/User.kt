package com.iron.espresso.domain.entity

data class User(
    val id: Int?,
    val nickname: String?,
    val email: String?,
    val image: String?,
    val introduce: String?,
    val location: String?,
    val careerTitle: String?,
    val careerContents: String?,
    val snsGithub: String?,
    val snsLinkedin: String?,
    val snsWeb: String?,
    val emailVerified: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val project: List<Any>?
)