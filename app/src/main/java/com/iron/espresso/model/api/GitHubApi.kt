package com.iron.espresso.model.api

import com.iron.espresso.model.response.GitHubUserResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String): Single<GitHubUserResponse>

    companion object {
        const val BASE_URL = "https://api.github.com"
        private const val BASE_V3_HEADER = "Accept: application/vnd.github.v3+json"

        fun create(): GitHubApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApi::class.java)
        }
    }

}
