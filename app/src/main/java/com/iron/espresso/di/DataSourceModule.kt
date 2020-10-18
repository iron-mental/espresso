package com.iron.espresso.di

import com.iron.espresso.model.source.remote.ProfileRemoteDataSource
import com.iron.espresso.model.source.remote.ProfileRemoteDataSourceImpl
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import com.iron.espresso.model.source.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.koin.dsl.module
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindProfileRemoteDataSource(profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource
}


val dataSourceModule = module {
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
}