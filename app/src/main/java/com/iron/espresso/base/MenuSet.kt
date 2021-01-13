package com.iron.espresso.base

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.iron.espresso.R

enum class MenuSet(@DrawableRes val imageResId: Int, @IdRes val menuId: Int, @StringRes val titleResId: Int) {
    ICON_SHARE(R.drawable.ic_share_24, R.id.menu_item_share, R.string.share),
    ICON_DONE(R.drawable.ic_done_24, R.id.menu_item_done, R.string.done)
}