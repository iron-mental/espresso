package com.iron.espresso.model.repo

import com.iron.espresso.domain.entity.Address
import com.iron.espresso.domain.entity.User
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

    override fun logout(): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.logout()

    override fun getUser(id: Int): Single<User> =
        userRemoteDataSource.getUser(id)
            .map { response ->
                if (!response.result) error(response.message.orEmpty())
                response.data?.toUser()
            }

    override fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.registerUser(email, password, nickname)

    override fun deleteUser(email: String, password: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.deleteUser(email, password)

    override fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateEmail(email)

    override fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>> =
        userRemoteDataSource.checkDuplicateNickname(nickname)

    override fun modifyUserImage(image: File?): Single<Boolean> {
        return userRemoteDataSource.modifyUserImage(image)
            .map {
                it.result
            }
    }

    override fun modifyUserInfo(nickname: String?, introduce: String): Single<Boolean> {
        return userRemoteDataSource.modifyUserInfo(nickname, introduce)
            .map {
                it.result
            }
    }

    override fun modifyUserEmail(email: String): Completable {
        return userRemoteDataSource.modifyUserEmail(email)
            .ignoreElement()
    }

    override fun modifyUserCareer(title: String, contents: String): Completable {
        return userRemoteDataSource.modifyUserCareer(title, contents)
            .ignoreElement()
    }

    override fun modifyUserSns(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Completable {
        return userRemoteDataSource.modifyUserSns(githubUrl, linkedInUrl, webUrl)
            .ignoreElement()
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

    override fun verifyEmail(): Single<BaseResponse<Nothing>> {
        return userRemoteDataSource.verifyEmail()
    }
}