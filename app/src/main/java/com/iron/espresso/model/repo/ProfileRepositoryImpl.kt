package com.iron.espresso.model.repo

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.model.source.remote.ProfileRemoteDataSource
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource):
    ProfileRepository {

}