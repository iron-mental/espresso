package com.iron.espresso.domain.usecase

import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.repo.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUser @Inject constructor(private val profileRepository: ProfileRepository) {
    
    operator fun invoke(userId: String): Single<GithubUser> {
        return profileRepository.getUser(userId)
    }

//    companion object {
//        fun getInstance() = GetUser(ProfileRepositoryImpl.getInstance())
//    }
}