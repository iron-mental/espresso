package com.iron.espresso.di

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.repo.ProfileRepositoryImpl
import com.iron.espresso.model.repo.StudyRepositoryImpl
import com.iron.espresso.model.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository


    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindStudyRepository(studyRepositoryImpl: StudyRepositoryImpl): StudyRepository

}
