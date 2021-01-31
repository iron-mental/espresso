package com.wswon.picker.ext

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wswon.picker.R

/** 이미지 피커에서 Load 에 실패한 아이템 선택불가 처리 */
@BindingAdapter(value = ["bind:uriImg", "bind:loadFailed"])
fun ImageView.setUriImg(uri: Uri?, loadFailed: (uri: Uri) -> Unit) {
    uri ?: return
    Glide.with(context)
        .load(uri)
        .error(R.drawable.ic_error_outline_black_48dp)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .override(measuredWidth, measuredHeight)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadFailed(uri)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(this)
}

@BindingAdapter("bind:uriDetailImg")
fun ImageView.setUriDetailImg(uri: Uri?) {
    uri ?: return
    Glide.with(context)
        .load(uri)
        .error(R.drawable.ic_error_outline_grey_96dp)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
