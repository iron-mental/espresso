package com.iron.espresso.di

import com.iron.espresso.domain.repo.KakaoRepository
import com.iron.espresso.domain.repo.ProjectRepository
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.repo.KakaoRepositoryImpl
import com.iron.espresso.model.repo.ProjectRepositoryImpl
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
    abstract fun bindProjectRepository(profileRepositoryImpl: ProjectRepositoryImpl): ProjectRepository


    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindKakaoRepository(kakaoRepositoryImpl: KakaoRepositoryImpl): KakaoRepository

    @Singleton
    @Binds
    abstract fun bindStudyRepository(studyRepositoryImpl: StudyRepositoryImpl): StudyRepository
}
