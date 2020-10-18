package com.iron.espresso.di

import com.iron.espresso.domain.usecase.GetUser
import org.koin.dsl.module

val domainModule = module {
    single<GetUser> { GetUser(get()) }
}
import com.iron.espresso.domain.repo.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetUser(repository: ProfileRepository): GetUser {
        return GetUser(repository)
    }
}

