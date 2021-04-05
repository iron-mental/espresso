package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Project
import com.iron.espresso.domain.repo.ProjectRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMyProjectList @Inject constructor(private val projectRepository: ProjectRepository) {

    operator fun invoke(id: Int): Single<List<Project>> {
        return projectRepository.getProjectList(id = id)
    }
}