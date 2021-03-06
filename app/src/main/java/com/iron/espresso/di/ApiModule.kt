package com.iron.espresso.di

import com.iron.espresso.model.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    const val API_URL = "https://www.terminal-study.site"
    private const val KAKAO_URL = "https://dapi.kakao.com/"

    private fun getLoggingClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

    @Singleton
    @Provides
    fun provideProjectApi(): ProjectApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(NoticeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoApi(): KakaoApi {
        return Retrofit.Builder()
            .baseUrl(KAKAO_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(KakaoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAlertApi(): AlertApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingClient())
            .build()
            .create(AlertApi::class.java)
    }
}