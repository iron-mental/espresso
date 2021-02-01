package com.iron.espresso.di

import com.iron.espresso.domain.repo.KakaoRepository
import com.iron.espresso.domain.repo.ProjectRepository
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

    @Singleton
    @Provides
    fun provideGetMyProjectList(repository: ProjectRepository): GetMyProjectList {
        return GetMyProjectList(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateProjectList(repository: ProjectRepository): UpdateProjectList {
        return UpdateProjectList(repository)
    }

    @Singleton
    @Provides
    fun provideModifyUserImage(repository: UserRepository): ModifyUserImage {
        return ModifyUserImage(repository)
    }

    @Singleton
    @Provides
    fun provideModifyUserInfo(repository: UserRepository): ModifyUserInfo {
        return ModifyUserInfo(repository)
    }

    @Singleton
    @Provides
    fun provideModifyUserEmail(repository: UserRepository): ModifyUserEmail {
        return ModifyUserEmail(repository)
    }

    @Singleton
    @Provides
    fun provideModifyUserCareer(repository: UserRepository): ModifyUserCareer {
        return ModifyUserCareer(repository)
    }


    @Singleton
    @Provides
    fun provideModifyUserSns(repository: UserRepository): ModifyUserSns {
        return ModifyUserSns(repository)
    }

    @Singleton
    @Provides
    fun provideModifyUserLocation(userRepository: UserRepository, kakaoRepository: KakaoRepository): ModifyUserLocation {
        return ModifyUserLocation(userRepository, kakaoRepository)
    }

    @Singleton
    @Provides
    fun provideGetAddressList(repository: UserRepository): GetAddressList {
        return GetAddressList(repository)
    }
}

