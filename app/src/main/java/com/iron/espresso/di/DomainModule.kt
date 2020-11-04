package com.iron.espresso.di

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.domain.usecase.GetGithubUser
import com.iron.espresso.domain.usecase.GetUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.koin.dsl.module
import javax.inject.Singleton

val domainModule = module {
    single<GetUser> { GetUser(get()) }
}

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetUser(repository: ProfileRepository): GetGithubUser {
        return GetGithubUser(repository)
    }
}

