package com.iron.espresso.data.model

import com.iron.espresso.R
import javax.annotation.Resource

enum class NoticeItemType(@Resource val title: Int, @Resource val color: Int, val pinned: Boolean) {
    HEADER(R.string.pined_true, R.color.theme_fc813e, true),
    ITEM(R.string.pined_false, R.color.colorCobaltBlue, false)
}