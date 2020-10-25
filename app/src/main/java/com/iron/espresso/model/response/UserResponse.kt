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
    val emailVerified: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("project")
    val project: List<ProjectResponse> = emptyList()
) {
    fun toUser(): User =
        User(
            id ?: -1,
            nickname.orEmpty(),
            email.orEmpty(),
            image.orEmpty(),
            introduce.orEmpty(),
            location.orEmpty(),
            careerTitle.orEmpty(),
            careerContents.orEmpty(),
            snsGithub.orEmpty(),
            snsLinkedin.orEmpty(),
            snsWeb.orEmpty(),
            emailVerified ?: 0,
            createdAt.orEmpty(),
            updatedAt.orEmpty(),
            project.map { it.toProject() }
        )
}

