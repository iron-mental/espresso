package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.Address
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface UserRepository {
    fun login(
        email: String,
        password: String,
        pushToken: String
    ): Single<BaseResponse<UserAuthResponse>>

    fun registerUser(
        email: String,
        password: String,
        nickname: String
    ): Single<BaseResponse<Nothing>>

    fun checkDuplicateEmail(email: String): Single<BaseResponse<Nothing>>

    fun checkDuplicateNickname(nickname: String): Single<BaseResponse<Nothing>>

    fun modifyUserImage(image: File?): Completable

    fun modifyUserInfo(nickname: String, introduce: String): Completable

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
}