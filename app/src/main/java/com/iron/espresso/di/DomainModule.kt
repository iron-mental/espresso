package com.iron.espresso.di

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.domain.repo.UserRepository
import com.iron.espresso.domain.usecase.*
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
    fun provideGetUser(repository: ProfileRepository): GetGithubUser {
        return GetGithubUser(repository)
    }

    @Singleton
    @Provides
    fun provideLoginUser(repository: UserRepository): LoginUser {
        return LoginUser(repository)
    }

    @Singleton
    @Provides
    fun provideRegisterUser(repository: UserRepository): RegisterUser {
        return RegisterUser(repository)
    }

    @Singleton
    @Provides
    fun provideCheckDuplicateEmail(repository: UserRepository): CheckDuplicateEmail {
        return CheckDuplicateEmail(repository)
    }

    @Singleton
    @Provides
    fun provideCheckDuplicateNickname(repository: UserRepository): CheckDuplicateNickname {
        return CheckDuplicateNickname(repository)
    }
}

