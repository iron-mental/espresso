package com.iron.espresso.ext

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("android:visibleIf")
fun View.visibleIf(isVisible: Boolean?) {
    this.isVisible = isVisible ?: false
}

@BindingAdapter("android:invisibleIf")
fun View.invisibleIf(isInvisible: Boolean?) {
    this.isInvisible = isInvisible ?: false
}

@BindingAdapter("android:goneIf")
fun View.goneIf(isGone: Boolean?) {
    this.isGone = isGone ?: false
}

@BindingAdapter("android:activationIf")
fun View.activationIf(activation: Boolean?) {
    this.alpha = if (activation == true) 1F else 0.4F
}