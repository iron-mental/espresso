package com.iron.espresso.di

import com.iron.espresso.domain.usecase.GetUser
import org.koin.dsl.module

val domainModule = module {
    single<GetUser> { GetUser(get()) }
}