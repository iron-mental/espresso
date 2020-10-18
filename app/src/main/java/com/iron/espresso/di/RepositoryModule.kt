package com.iron.espresso.di

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.model.repo.ProfileRepositoryImpl
import com.iron.espresso.model.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.koin.dsl.module
import javax.inject.Singleton

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}
