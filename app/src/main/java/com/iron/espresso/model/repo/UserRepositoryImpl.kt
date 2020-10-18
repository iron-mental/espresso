package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun getUser(userId: String, userPass: String): User =
        userRemoteDataSource.getUser(userId, userPass).toUser()
}