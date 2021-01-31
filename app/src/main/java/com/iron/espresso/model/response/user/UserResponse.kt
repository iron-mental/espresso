package com.iron.espresso.model.response.user


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
    @SerializedName("sido")
    val sido: String?,
    @SerializedName("sigungu")
    val siGungu: String?,
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
    @SerializedName("email_verified")
    val emailVerified: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?
) {

    fun toUser() =
        User(
            id = id ?: -1,
            nickname = nickname.orEmpty(),
            email = email.orEmpty(),
            image = image.orEmpty(),
            introduce = introduce.orEmpty(),
            sido = sido.orEmpty(),
            siGungu = siGungu.orEmpty(),
            careerTitle = careerTitle.orEmpty(),
            careerContents = careerContents.orEmpty(),
            snsGithub = snsGithub.orEmpty(),
            snsLinkedin = snsLinkedin.orEmpty(),
            snsWeb = snsWeb.orEmpty(),
            emailVerified = emailVerified ?: false,
            createdAt = createdAt.orEmpty(),
        )
}