package com.iron.espresso.di

import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.repo.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}