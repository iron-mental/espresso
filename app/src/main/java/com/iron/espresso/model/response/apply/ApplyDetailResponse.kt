package com.iron.espresso.model.response.apply


import com.google.gson.annotations.SerializedName

data class ApplyDetailResponse(
    @SerializedName("project")
    val projectResponseList: List<ProjectResponse>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("study_id")
    val studyId: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("rejected_status")
    val rejectedStatus: Boolean?,
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
)