package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.GithubUser
import io.reactivex.Single

interface ProfileRepository {
    fun getUser(userId: String): Single<GithubUser>
}
