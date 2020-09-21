package com.iron.espresso.di

import com.iron.espresso.model.source.remote.ProfileRemoteDataSource
import com.iron.espresso.model.source.remote.ProfileRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindProfileRemoteDataSource(profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource
}
