package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.Address
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {
    override fun login(
        email: String,
        password: String,
        pushToken: String
    ): Single<BaseResponse<UserAuthResponse>> =
        userRemoteDataSource.login(email, password, pushToken)

    override fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.registerUser(email, password, nickname)


    override fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateNickname(nickname)

    override fun modifyUserImage(image: File?): Completable {
        TODO("Not yet implemented")
    }

    override fun modifyUserInfo(nickname: String, introduce: String): Completable {
        TODO("Not yet implemented")
    }

    override fun modifyUserEmail(email: String): Completable {
        TODO("Not yet implemented")
    }

    override fun modifyUserCareer(title: String, contents: String): Completable {
        TODO("Not yet implemented")
    }

    override fun modifyUserSns(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Completable {
        TODO("Not yet implemented")
    }

    override fun modifyUserLocation(
        latitude: Double,
        longitude: Double,
        sido: String,
        sigungu: String
    ): Single<BaseResponse<Nothing>> {
        return userRemoteDataSource.modifyUserLocation(latitude, longitude, sido, sigungu)
    }

    override fun getAddressList(): Single<List<Address>> {
        return userRemoteDataSource.getAddressList()
            .map { response ->
                if (!response.result) error(response.message.orEmpty())
                response.data?.map { it.toAddress() }
            }
    }
}