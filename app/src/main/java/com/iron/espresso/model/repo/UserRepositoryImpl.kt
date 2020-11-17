package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import io.reactivex.Single

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun login(email: String, password: String): Single<User> =
        userRemoteDataSource.login(email, password).map {
            null
        }

    override fun registerUser(email: String, password: String, nickname: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.registerUser(email, password, nickname)


    override fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateNickname(nickname)
}