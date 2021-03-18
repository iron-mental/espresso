package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.repo.UserRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(email: String, password: String) =
        userRepository.deleteUser(email, password)

}