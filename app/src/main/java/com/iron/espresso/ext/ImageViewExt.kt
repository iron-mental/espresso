package com.iron.espresso.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.R

@BindingAdapter("bind:setUrlImg", "bind:type")
fun ImageView.setUrlImg(url: String?, type: ImageType?) {
    when (type) {
        ImageType.NORMAl -> {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .error(R.drawable.dummy_image)
                .into(this)
        }
        ImageType.ROUND -> {
            Glide.with(context)
                .load(R.drawable.android)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.dummy_image)
                .into(this)
        }
    }
}


@BindingAdapter("bind:setStudyCategoryImg")
fun ImageView.setStudyCategoryImg(drawable: Int?) {
    Glide.with(context)
        .load(drawable)
        .fitCenter()
        .error(R.drawable.dummy_image)
        .into(this)
}


enum class ImageType {
    ROUND, NORMAl
}