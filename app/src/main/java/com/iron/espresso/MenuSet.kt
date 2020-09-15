package com.iron.espresso

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

enum class MenuSet(@DrawableRes val imageResId: Int, @IdRes val menuId: Int, @StringRes val titleResId: Int) {
    ICON_SHARE(R.drawable.ic_share_24, R.id.menu_item_share, R.string.more)
}