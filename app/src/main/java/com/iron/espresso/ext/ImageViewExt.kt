package com.iron.espresso.ext

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iron.espresso.AuthHolder

fun ImageView.setImage(imageUri: Uri) {
    Glide.with(context)
        .load(imageUri)
        .into(this)
}

fun ImageView.setImage(imageUrl: String, centerCrop: Boolean = false) {
    if (imageUrl.isEmpty()) return

    Glide.with(context)
        .load(
            GlideUrl(
                imageUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", AuthHolder.bearerToken)
                    .build()
            )
        )
        .apply {
            if (centerCrop) {
                centerCrop()
            }
        }
        .into(this)
}

@BindingAdapter("bind:setCircleImage")
fun ImageView.setCircleImage(imageUrl: String) {
    if (imageUrl.isEmpty()) return

    Glide.with(context)
        .load(
            GlideUrl(
                imageUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", AuthHolder.bearerToken)
                    .build()
            )
        )
        .optionalCircleCrop()
        .into(this)
}

fun ImageView.setCircleImage(imageUri: Uri) {
    Glide.with(context)
        .load(imageUri)
        .optionalCircleCrop()
        .into(this)
}

@BindingAdapter("bind:setRadiusImage")
fun ImageView.setRadiusImage(imageUrl: String) {
    if (imageUrl.isEmpty()) return

    Glide.with(context)
        .load(
            GlideUrl(
                imageUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", AuthHolder.bearerToken)
                    .build()
            )
        )
        .transform(CenterCrop(), RoundedCorners(30))
        .into(this)
}
