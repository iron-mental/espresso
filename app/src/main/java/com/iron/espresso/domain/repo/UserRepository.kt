package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.AccessToken
import com.iron.espresso.domain.entity.Address
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.entity.Version
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.io.File

interface UserRepository {
    fun login(
        email: String,
        password: String,
        pushToken: String
    ): Single<BaseResponse<UserAuthResponse>>

    fun logout(): Single<Pair<Boolean, String>>

    fun getUser(id: Int): Single<User>

    fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>>

    fun deleteUser(email: String, password: String): Single<Pair<Boolean, String>>

    fun checkDuplicateEmail(email: String): Single<Boolean>

    fun checkDuplicateNickname(nickname: String): Single<Pair<Boolean, String>>

    fun modifyUserImage(image: File?): Single<Boolean>

    fun modifyUserInfo(nickname: String?, introduce: String): Single<Boolean>

    fun modifyUserEmail(email: String): Completable

    fun modifyUserCareer(title: String, contents: String): Completable

    fun modifyUserSns(
        githubUrl: String,
        linkedInUrl: String,
        webUrl: String
    ): Completable

    fun modifyUserLocation(
        latitude: Double,
        longitude: Double,
        sido: String,
        sigungu: String
    ): Single<BaseResponse<Nothing>>

    fun getAddressList(): Single<List<Address>>

    fun verifyEmail(): Single<Pair<Boolean, String>>

    fun reIssuanceAccessToken(refreshToken: String): Single<AccessToken>

    fun getVersionInfo(version: String): Single<Version>
}