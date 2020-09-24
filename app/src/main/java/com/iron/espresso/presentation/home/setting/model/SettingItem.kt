package com.iron.espresso.presentation.home.setting.model

data class SettingItem(
    val title: String,
    val subItemType: SubItemType?
) : ItemType {
    override val type: SettingItemType
        get() = SettingItemType.ITEM
}