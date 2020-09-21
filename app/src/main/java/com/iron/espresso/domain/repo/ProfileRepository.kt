package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.GithubUser
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
interface ProfileRepository {
    fun getUser(userId: String): Single<GithubUser>
}
