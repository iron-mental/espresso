package com.iron.espresso.data.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.iron.espresso.R

enum class NoticeItemType(@StringRes val title: Int, @ColorRes val color: Int, val pinned: Boolean) {
    HEADER(R.string.pined_true, R.color.theme_fc813e, true),
    ITEM(R.string.pined_false, R.color.colorCobaltBlue, false)
}