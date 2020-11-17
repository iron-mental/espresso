package com.iron.espresso.di

import com.iron.espresso.model.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {
    private const val BASE_URL = "https://api.github.com"
    private const val BASE_V3_HEADER = "Accept: application/vnd.github.v3+json"

    private const val API_URL = "http://3.35.154.27:3000"

    private fun getLoggingClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProjectApi(): ProjectApi {

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(ProjectApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStudyApi(): StudyApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(StudyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApplyApi(): ApplyApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(ApplyApi::class.java)
    }


    @Singleton
    @Provides
    fun provideNoticeApi(): NoticeApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(NoticeApi::class.java)
    }
}