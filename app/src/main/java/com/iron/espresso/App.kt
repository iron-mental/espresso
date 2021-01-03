package com.iron.espresso

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun context(): Context = applicationContext


    companion object {
        lateinit var instance: App
            private set

    }
}