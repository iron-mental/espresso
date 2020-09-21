package com.iron.espresso.model.api

import com.iron.espresso.model.response.GitHubUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String): Single<GitHubUserResponse>
}
