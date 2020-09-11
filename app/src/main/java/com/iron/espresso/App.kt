package com.iron.espresso

import android.app.Application
import com.iron.espresso.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
                    viewModelModule
                )
            )
        }
    }
}