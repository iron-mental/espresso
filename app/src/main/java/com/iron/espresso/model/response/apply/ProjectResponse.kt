package com.iron.espresso.model.response.apply


import com.google.gson.annotations.SerializedName

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
    val snsPlaystore: String?
)