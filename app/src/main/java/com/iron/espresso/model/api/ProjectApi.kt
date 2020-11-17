package com.iron.espresso.model.api

import com.google.gson.annotations.SerializedName
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.project.ProjectListResponse
import io.reactivex.Single
import retrofit2.http.*


data class RegisterProjectRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("sns_github")
    val snsGithub: String? = null,
    @SerializedName("sns_appstore")
    val snsAppstore: String? = null,
    @SerializedName("sns_playstore")
    val snsPlaystore: String? = null
)

data class ModifyProjectRequest(
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
)

interface ProjectApi {

    @POST("/v1/user/{id}/project")
    fun registerProject(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body body: RegisterProjectRequest
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}/project")
    fun getProjectList(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
    ): Single<BaseResponse<ProjectListResponse>>

    @PUT("/v1/user/{id}/project/{project_id}")
    fun modifyProject(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Path("project_id") projectId: Int,
        @Body body: ModifyProjectRequest
    ): Single<BaseResponse<Nothing>>

    @DELETE("/v1/user/{id}/project/{project_id}")
    fun deleteProject(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Path("project_id") projectId: Int
    ): Single<BaseResponse<Nothing>>
}