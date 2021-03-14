package com.iron.espresso.model.response.project


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Project
import com.iron.espresso.presentation.profile.ProjectItem

data class ProjectResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("sns_github")
    val snsGithub: String?,
    @SerializedName("sns_appstore")
    val snsAppstore: String?,
    @SerializedName("sns_playstore")
    val snsPlaystore: String?,
    @SerializedName("created_at")
    val createdAt: String?
) {
    fun toProject(): Project =
        Project(
            id ?: -1,
            title.orEmpty(),
            contents.orEmpty(),
            snsGithub.orEmpty(),
            snsAppstore.orEmpty(),
            snsPlaystore.orEmpty()
        )

    fun toProjectItem(): ProjectItem =
        ProjectItem(
            id ?: -1,
            title.orEmpty(),
            contents.orEmpty(),
            snsGithub.orEmpty(),
            snsAppstore.orEmpty(),
            snsPlaystore.orEmpty()
        )
}