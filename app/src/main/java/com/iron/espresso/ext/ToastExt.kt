package com.iron.espresso.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, isLong: Boolean = false) {
    context?.let {
        Toast.makeText(
            it, message,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }
}

fun Activity.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(
        this, message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}