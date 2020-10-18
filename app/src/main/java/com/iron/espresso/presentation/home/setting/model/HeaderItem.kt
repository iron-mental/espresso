package com.iron.espresso.presentation.home.setting.model

data class HeaderItem(
    val imageUrl: String,
    val nickName: String,
    val location: String,
    val announce: String
) : ItemType {
    override val type: SettingItemType
        get() = SettingItemType.HEADER
}