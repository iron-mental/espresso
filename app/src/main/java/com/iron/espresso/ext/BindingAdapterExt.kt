package com.iron.espresso.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:src")
fun ImageView.setBindResource(@DrawableRes resource: Int){
    this.setImageResource(resource)
}