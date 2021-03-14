package com.iron.espresso.model.response.apply

import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.ApplyDetail
import com.iron.espresso.model.response.project.ProjectResponse

data class ApplyDetailResponse(
    @SerializedName("project")
    val projectResponseList: List<ProjectResponse>?,
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("user_id")
    val userId: Int = -1,
    @SerializedName("study_id")
    val studyId: Int = -1,
    @SerializedName("message")
    val message: String?,
    @SerializedName("rejected_status")
    val rejectedStatus: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("rejected_at")
    val rejectedAt: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("sns_github")
    val snsGithub: String?,
    @SerializedName("sns_linkedin")
    val snsLinkedin: String?,
    @SerializedName("sns_web")
    val snsWeb: String?
) {
    fun toApplyDetail(): ApplyDetail =
        ApplyDetail(
            projectList = projectResponseList?.map { it.toProject() }.orEmpty(),
            id = id,
            userId = userId,
            studyId = studyId,
            message = message.orEmpty(),
            rejectedStatus = rejectedStatus,
            createdAt = createdAt.orEmpty(),
            rejectedAt = rejectedAt.orEmpty(),
            image = image.orEmpty(),
            nickname = nickname.orEmpty(),
            snsGithub = snsGithub.orEmpty(),
            snsLinkedin = snsLinkedin.orEmpty(),
            snsWeb = snsWeb.orEmpty()
        )
}