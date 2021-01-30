package com.iron.espresso.model.api

import com.google.gson.annotations.SerializedName
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.project.ProjectListResponse
import com.iron.espresso.presentation.profile.ProjectItem
import io.reactivex.Single
import retrofit2.http.*

data class ModifyProjectRequest(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("contents")
    val contents: String? = null,
    @SerializedName("sns_github")
    val snsGithub: String? = null,
    @SerializedName("sns_appstore")
    val snsAppstore: String? = null,
    @SerializedName("sns_playstore")
    val snsPlaystore: String? = null
) {
    companion object {
        fun of(item: ProjectItem): ModifyProjectRequest =
            ModifyProjectRequest(
                id = if (item.id == -1) null else item.id,
                title = item.title,
                contents = item.contents,
                snsGithub = if (item.githubUrl.isEmpty()) null else item.githubUrl,
                snsAppstore = if (item.appStoreUrl.isEmpty()) null else item.appStoreUrl,
                snsPlaystore = if (item.playStoreUrl.isEmpty()) null else item.playStoreUrl
            )
    }
}

interface ProjectApi {

    @POST("/v1/user/{id}/project")
    fun registerProject(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: List<ModifyProjectRequest>
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}/project")
    fun getProjectList(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
    ): Single<BaseResponse<ProjectListResponse>>

    @DELETE("/v1/user/{id}/project/{project_id}")
    fun deleteProject(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Path("project_id") projectId: Int
    ): Single<BaseResponse<Nothing>>
}