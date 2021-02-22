package com.iron.espresso.domain.entity

data class ApplyDetail(
    val projectList: List<Project>,
    val id: Int,
    val userId: Int,
    val studyId: Int,
    val message: String,
    val rejectedStatus: Boolean,
    val createdAt: String,
    val rejectedAt: String,
    val image: String,
    val nickname: String,
    val snsGithub: String,
    val snsLinkedin: String,
    val snsWeb: String
)