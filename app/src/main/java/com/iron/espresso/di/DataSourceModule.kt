package com.iron.espresso.di

import com.iron.espresso.model.source.remote.UserRemoteDataSource
import com.iron.espresso.model.source.remote.UserRemoteDataSourceImpl
import org.koin.dsl.module


val dataSourceModule = module {
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
}