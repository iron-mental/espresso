package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.Project
import com.iron.espresso.domain.repo.ProjectRepository
import com.iron.espresso.model.source.remote.ProjectRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class ProjectRepositoryImpl @Inject constructor(private val remoteDataSource: ProjectRemoteDataSource) :
    ProjectRepository {

    override fun updateProjectList(projectList: List<Project>): Completable {
        return remoteDataSource.updateProjectList(projectList)
            .filter { it.result }
            .ignoreElement()
    }

    override fun getProjectList(id: Int): Single<List<Project>> {
        return remoteDataSource.getProjectList(id)
            .map { response ->
                if (!response.result) error(response.message.orEmpty())
                response.data.orEmpty().map { it.toProject() }
            }
    }
}