package com.iron.espresso

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@App)
//            this.modules(viewModelModule)
//        }
    }
}