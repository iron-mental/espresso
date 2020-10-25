package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import io.reactivex.Single

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun getUser(userId: String, password: String): Single<User> =
        userRemoteDataSource.getUser(userId, password).map {
            it.toUser()
        }

    override fun registerUser(userId: String, password: String, nickname: String): Single<MessageResponse> =
        userRemoteDataSource.registerUser(userId, password, nickname)


    override fun checkDuplicateEmail(email: String): Single<MessageResponse> =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<MessageResponse> =
        userRemoteDataSource.checkDuplicateNickname(nickname)
}