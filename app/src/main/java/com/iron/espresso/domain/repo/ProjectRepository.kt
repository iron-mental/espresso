package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.Project
import io.reactivex.Completable
import io.reactivex.Single

interface ProjectRepository {

    fun updateProjectList(projectList: List<Project>): Completable

    fun getProjectList(id: Int): Single<List<Project>>
}
