package com.iron.espresso.data.model

data class NoticeListHeaderItem (
    val category: String,
    val title: String,
    val content: String,
    val version: String
): ItemType{
    override val noticeItemType: NoticeItemType
        get() = NoticeItemType.HEADER
}