package com.iron.espresso

import android.app.Application
import com.iron.espresso.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    dataSourceModule,
                    domainModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}