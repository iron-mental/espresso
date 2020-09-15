package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.GitHubApi
import com.iron.espresso.model.response.GitHubUserResponse
import io.reactivex.Single

class ProfileRemoteDataSource(private val gitHubApi: GitHubApi = GitHubApi.create()) {

    fun getUser(userId: String): Single<GitHubUserResponse> {
        return gitHubApi.getUser(userId)
    }

    companion object {
        fun getInstance() =
            ProfileRemoteDataSource()
    }
}
