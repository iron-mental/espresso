package com.iron.espresso.di

import com.iron.espresso.model.api.UserApi
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val USER_URL = "http://3.35.154.27:3000"


val networkModule = module {

    single<UserApi> {
        get<Retrofit> { parametersOf(USER_URL) }
            .create(UserApi::class.java)
    }

    factory<Retrofit> { (url: String) ->
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(get())
            .build()
    }

    single<Converter.Factory> { GsonConverterFactory.create() }

}