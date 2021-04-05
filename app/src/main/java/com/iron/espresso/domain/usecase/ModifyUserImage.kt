package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.rxjava3.core.Single
import java.io.File
import javax.inject.Inject

class ModifyUserImage @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(image: File?): Single<Boolean> {
        return userRepository.modifyUserImage(image)
    }
}