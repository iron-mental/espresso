package com.iron.espresso.domain.entity

data class User(
    val id: Int,
    val nickname: String,
    val email: String,
    val image: String,
    val introduce: String,
    val address: String,
    val careerTitle: String,
    val careerContents: String,
    val snsGithub: String,
    val snsLinkedin: String,
    val snsWeb: String,
    val emailVerified: Boolean,
    val createdAt: String
)