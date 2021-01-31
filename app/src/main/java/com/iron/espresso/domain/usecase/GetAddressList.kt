package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.Address
import com.iron.espresso.domain.repo.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAddressList @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Single<List<Address>>{
        return userRepository.getAddressList()
    }
}