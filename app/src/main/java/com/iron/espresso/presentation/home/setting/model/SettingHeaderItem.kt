package com.iron.espresso.presentation.home.setting.model

data class SettingHeaderItem(
    val categoryTitle: String,
    val items: List<SettingItem>
) : ItemType {
    override val type: SettingItemType
        get() = SettingItemType.ITEM_HEADER
}

