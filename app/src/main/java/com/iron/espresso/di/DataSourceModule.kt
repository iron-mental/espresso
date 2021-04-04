package com.iron.espresso.di

import com.iron.espresso.model.source.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindProjectRemoteDataSource(projectRemoteDataSourceImpl: ProjectRemoteDataSourceImpl): ProjectRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindApplyRemoteDataSource(applyRemoteDataSourceImpl: ApplyRemoteDataSourceImpl): ApplyRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindKakaoRemoteDataSource(kakaoRemoteDataSourceImpl: KakaoRemoteDataSourceImpl): KakaoRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindStudyRemoteDataSource(studyRemoteDataSourceImpl: StudyRemoteDataSourceImpl): StudyRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindAlertRemoteDataSource(alertRemoteDataSourceImpl: AlertRemoteDataSourceImpl): AlertRemoteDataSource
}
