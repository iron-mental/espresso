package com.iron.espresso.presentation.home.setting.model

data class SettingHeaderItem(
    val categoryTitle: String
) : ItemType {
    override val type: SettingItemType
        get() = SettingItemType.ITEM_HEADER
}

