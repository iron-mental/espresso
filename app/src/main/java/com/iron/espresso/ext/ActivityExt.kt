package com.iron.espresso.ext

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this.application, T::class.java)).apply { finish() }
}