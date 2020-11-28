package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.MessageResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun login(email: String, password: String): Single<BaseResponse<UserAuthResponse>> =
        userRemoteDataSource.login(email, password)

    override fun registerUser(email: String, password: String, nickname: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.registerUser(email, password, nickname)


    override fun checkDuplicateEmail(email: String): Single<MessageResponse> =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<MessageResponse> =
        userRemoteDataSource.checkDuplicateNickname(nickname)
}