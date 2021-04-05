package com.iron.espresso.di

import com.iron.espresso.domain.repo.*
import com.iron.espresso.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideLoginUser(repository: UserRepository): LoginUser {
        return LoginUser(repository)
    }

    @Singleton
    @Provides
    fun provideGetUser(repository: UserRepository): GetUser {
        return GetUser(repository)
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
    fun provideReIssuanceToken(repository: UserRepository): ReIssuanceToken {
        return ReIssuanceToken(repository)
    }

    @Singleton
    @Provides
    fun provideGetVersionInfo(repository: UserRepository): GetVersionInfo {
        return GetVersionInfo(repository)
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
    fun provideModifyUserLocation(
        userRepository: UserRepository,
        kakaoRepository: KakaoRepository
    ): ModifyUserLocation {
        return ModifyUserLocation(userRepository, kakaoRepository)
    }

    @Singleton
    @Provides
    fun provideGetAddressList(repository: UserRepository): GetAddressList {
        return GetAddressList(repository)
    }

    @Singleton
    @Provides
    fun provideRegisterApply(repository: ApplyRepository): RegisterApply {
        return RegisterApply(repository)
    }

    @Singleton
    @Provides
    fun provideGetApplyOwner(repository: ApplyRepository): GetApplyOwner {
        return GetApplyOwner(repository)
    }

    @Singleton
    @Provides
    fun provideGetApplyByApplier(repository: ApplyRepository): GetApplyByApplier {
        return GetApplyByApplier(repository)
    }

    @Singleton
    @Provides
    fun provideGetApplyList(repository: ApplyRepository): GetApplyList {
        return GetApplyList(repository)
    }

    @Singleton
    @Provides
    fun provideGetMyApplyList(repository: ApplyRepository): GetMyApplyList {
        return GetMyApplyList(repository)
    }

    @Singleton
    @Provides
    fun provideModifyApply(repository: ApplyRepository): ModifyApply {
        return ModifyApply(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteApply(repository: ApplyRepository): DeleteApply {
        return DeleteApply(repository)
    }

    @Singleton
    @Provides
    fun provideHandleApply(repository: ApplyRepository): HandleApply {
        return HandleApply(repository)
    }

    @Singleton
    @Provides
    fun provideGetAlertList(repository: AlertRepository): GetAlertList {
        return GetAlertList(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteChat(repository: ChatRepository): DeleteChat {
        return DeleteChat(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteAllChat(repository: ChatRepository): DeleteAllChat {
        return DeleteAllChat(repository)
    }
}

