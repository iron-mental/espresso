package com.iron.espresso.domain.entity

data class User(
    val id: Int,
    val nickname: String,
    val email: String,
    val image: String,
    val introduce: String,
    val sido: String,
    val siGungu: String,
    val careerTitle: String,
    val careerContents: String,
    val snsGithub: String,
    val snsLinkedin: String,
    val snsWeb: String,
    val emailVerified: Boolean,
    val createdAt: String
) {
    val isSnsEmpty: Boolean
        get() = snsGithub.isEmpty() && snsLinkedin.isEmpty() && snsWeb.isEmpty()
}