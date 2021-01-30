package com.iron.espresso.domain.usecase

import com.iron.espresso.AuthHolder
import com.iron.espresso.domain.entity.Project
import com.iron.espresso.domain.repo.ProjectRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMyProjectList @Inject constructor(private val projectRepository: ProjectRepository) {

    operator fun invoke(): Single<List<Project>> {
        return projectRepository.getProjectList(id = AuthHolder.requireId())
    }
}