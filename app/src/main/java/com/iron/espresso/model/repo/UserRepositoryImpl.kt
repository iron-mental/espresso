package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun getUser(userId: String, userPass: String): User =
        userRemoteDataSource.getUser(userId, userPass).toUser()

    override fun registerUser(userId: String, userPass: String, nickname: String): Boolean =
        userRemoteDataSource.registerUser(userId, userPass, nickname)


    override fun checkDuplicateEmail(email: String): String =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): String =
        userRemoteDataSource.checkDuplicateNickname(nickname)
}