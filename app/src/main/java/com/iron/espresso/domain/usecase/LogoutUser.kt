package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.response.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class LogoutUser @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Single<BaseResponse<Nothing>> =
        userRepository.logout()
}