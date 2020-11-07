package com.iron.espresso.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
