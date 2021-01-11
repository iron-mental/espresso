package com.wswon.picker.ext

import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {
    isVisible = value
}

@BindingAdapter("android:invisibleIf")
fun View.setInvisibleIf(value: Boolean) {
    isInvisible = value
}

@BindingAdapter("android:goneIf")
fun View.setGoneIf(value: Boolean) {
    isGone = value
}


@BindingAdapter("date")
fun TextView.setDate(timeStamp: Long?) {
    if (timeStamp != null) {
        text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(timeStamp))
    }
}