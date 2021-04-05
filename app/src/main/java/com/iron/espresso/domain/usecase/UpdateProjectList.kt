package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Project
import com.iron.espresso.domain.repo.ProjectRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UpdateProjectList @Inject constructor(private val projectRepository: ProjectRepository) {

    operator fun invoke(projectList: List<Project>): Completable {
        return projectRepository.updateProjectList(projectList)
    }

}