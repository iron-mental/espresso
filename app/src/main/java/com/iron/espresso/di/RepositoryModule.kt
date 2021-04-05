package com.iron.espresso.di

import com.iron.espresso.domain.repo.*
import com.iron.espresso.local.model.ChatRepositoryImpl
import com.iron.espresso.model.repo.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProjectRepository(profileRepositoryImpl: ProjectRepositoryImpl): ProjectRepository


    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindApplyRepository(applyRepositoryImpl: ApplyRepositoryImpl): ApplyRepository

    @Singleton
    @Binds
    abstract fun bindKakaoRepository(kakaoRepositoryImpl: KakaoRepositoryImpl): KakaoRepository

    @Singleton
    @Binds
    abstract fun bindStudyRepository(studyRepositoryImpl: StudyRepositoryImpl): StudyRepository

    @Singleton
    @Binds
    abstract fun bindAlertRepository(alertRepositoryImpl: AlertRepositoryImpl): AlertRepository

    @Singleton
    @Binds
    abstract fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}
