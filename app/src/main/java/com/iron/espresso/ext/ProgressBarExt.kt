package com.iron.espresso.ext

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("toggleVisible")
fun ProgressBar.toggleVisible(toggle: Boolean) {
    isVisible = toggle
}