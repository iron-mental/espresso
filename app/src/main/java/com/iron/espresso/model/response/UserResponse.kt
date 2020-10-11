package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.User

data class UserResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("introduce")
    val introduce: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("career_title")
    val careerTitle: String?,
    @SerializedName("career_contents")
    val careerContents: String?,
    @SerializedName("sns_github")
    val snsGithub: String?,
    @SerializedName("sns_linkedin")
    val snsLinkedin: String?,
    @SerializedName("sns_web")
    val snsWeb: String?,
    @SerializedName("emailVerified")
    val emailVerified: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("project")
    val project: List<Any>?
) {
    fun toUser(): User =
        User(
            id,
            nickname,
            email,
            image,
            introduce,
            location,
            careerTitle,
            careerContents,
            snsGithub,
            snsLinkedin,
            snsWeb,
            emailVerified,
            createdAt,
            updatedAt,
            project
        )
}

