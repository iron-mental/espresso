package com.iron.espresso.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.iron.espresso.R

@BindingAdapter("bind:setUrlImg")
fun ImageView.setUrlImg(url: String?) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .error(R.drawable.dummy_image)
        .into(this)
}