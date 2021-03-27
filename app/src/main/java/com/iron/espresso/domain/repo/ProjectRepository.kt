package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.Project
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProjectRepository {

    fun updateProjectList(projectList: List<Project>): Completable

    fun getProjectList(id: Int): Single<List<Project>>
}
