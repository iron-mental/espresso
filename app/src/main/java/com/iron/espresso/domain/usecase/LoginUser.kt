package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, userPass: String, pushToken: String): Single<BaseResponse<UserAuthResponse>> =
        userRepository.login(email, userPass, pushToken)
}