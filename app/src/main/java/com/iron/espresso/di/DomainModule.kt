package com.iron.espresso.di

import com.iron.espresso.domain.repo.ProfileRepository
import com.iron.espresso.domain.usecase.GetUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Provides
    fun provideGetUser(repository: ProfileRepository): GetUser {
        return GetUser(repository)
    }
}

