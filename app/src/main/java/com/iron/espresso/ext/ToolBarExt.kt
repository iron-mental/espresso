package com.iron.espresso.ext

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

@BindingAdapter("navigationClick")
fun MaterialToolbar.setNavigationOnClickListener(f: () -> Unit?) {
    setNavigationOnClickListener {
        f.invoke()
    }
}