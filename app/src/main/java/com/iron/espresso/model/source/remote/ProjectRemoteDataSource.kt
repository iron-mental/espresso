package com.iron.espresso.model.source.remote

import com.iron.espresso.AuthHolder
import com.iron.espresso.domain.entity.Project
import com.iron.espresso.model.api.ModifyProjectListRequest
import com.iron.espresso.model.api.ModifyProjectRequest
import com.iron.espresso.model.api.ProjectApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.project.ProjectListResponse
import io.reactivex.Single
import javax.inject.Inject

class ProjectRemoteDataSourceImpl @Inject constructor(private val projectApi: ProjectApi) :
    ProjectRemoteDataSource {

    override fun updateProjectList(projectList: List<Project>): Single<BaseResponse<Nothing>> {
        return projectApi.updateProject(id = AuthHolder.requireId(), body = ModifyProjectListRequest(projectList.map { ModifyProjectRequest.of(it) }))
    }

    override fun getProjectList(id: Int): Single<BaseResponse<ProjectListResponse>> {
        return projectApi.getProjectList(id = id)
    }
}

interface ProjectRemoteDataSource {

    fun updateProjectList(projectList: List<Project>): Single<BaseResponse<Nothing>>

    fun getProjectList(id: Int): Single<BaseResponse<ProjectListResponse>>

//    fun deleteProject(projectId: Int): Single<BaseResponse<Nothing>>
}
