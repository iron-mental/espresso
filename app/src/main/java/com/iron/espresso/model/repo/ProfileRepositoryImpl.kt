package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.model.source.remote.ProfileRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource):
    ProfileRepository {

    override fun getUser(userId: String): Single<GithubUser> {
        return remoteDataSource.getUser(userId)
            .map {
                GithubUser(it.name.orEmpty(), it.avatarUrl.orEmpty())
            }
    }
}