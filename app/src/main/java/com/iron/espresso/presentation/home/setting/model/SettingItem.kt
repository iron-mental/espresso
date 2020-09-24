package com.iron.espresso.presentation.home.setting.model

data class SettingItem(
    val title: String
) : ItemType{
    override val type: SettingItemType
        get() = SettingItemType.ITEM
}