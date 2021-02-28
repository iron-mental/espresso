package com.iron.espresso.ext

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.iron.espresso.AuthHolder
import com.iron.espresso.R

@BindingAdapter("bind:setUrlImg", "bind:type")
fun ImageView.setUrlImg(url: String?, type: ImageType? = ImageType.NORMAl) {
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


fun ImageView.load(
    drawable: Int,
    loadOnlyFromCache: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }
    setStudyCategoryImg(drawable)
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

fun ImageView.setImage(imageUri: Uri) {
    Glide.with(context)
        .load(imageUri)
        .into(this)
}

fun ImageView.setCircleImage(imageUri: Uri) {
    Glide.with(context)
        .load(imageUri)
        .optionalCircleCrop()
        .into(this)
}

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

