package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.GitHubApi
import com.iron.espresso.model.response.GitHubUserResponse
import io.reactivex.Single
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi) :
    ProfileRemoteDataSource {

    override fun getUser(userId: String): Single<GitHubUserResponse> {
        return gitHubApi.getUser(userId)
    }
}

interface ProfileRemoteDataSource {
    fun getUser(userId: String): Single<GitHubUserResponse>
}
