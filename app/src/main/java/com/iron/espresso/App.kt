package com.iron.espresso

import android.app.Application
import android.content.Context
import com.iron.espresso.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin()
    }

    fun context(): Context = applicationContext

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

    companion object {
        lateinit var instance: App
            private set

    }
}