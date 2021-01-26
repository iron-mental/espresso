package com.iron.espresso.di

import com.iron.espresso.model.source.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindProfileRemoteDataSource(profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindStudyRemoteDataSource(studyRemoteDataSourceImpl: StudyRemoteDataSourceImpl): StudyRemoteDataSource

}
