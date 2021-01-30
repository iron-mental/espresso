package com.iron.espresso.di

import com.iron.espresso.model.source.remote.ProjectRemoteDataSource
import com.iron.espresso.model.source.remote.ProjectRemoteDataSourceImpl
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import com.iron.espresso.model.source.remote.UserRemoteDataSourceImpl
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
    abstract fun bindProjectRemoteDataSource(projectRemoteDataSourceImpl: ProjectRemoteDataSourceImpl): ProjectRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

}
