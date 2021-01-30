package com.iron.espresso.presentation.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectItem(
    val id: Int = -1,
    val title: String = "",
    val contents: String = "",
    val githubUrl: String = "",
    val appStoreUrl: String = "",
    val playStoreUrl: String = ""
) : Parcelable {

    fun isEmptyItem(): Boolean =
        id == -1
            && title == ""
            && contents == ""
            && githubUrl == ""
            && appStoreUrl == ""
            && playStoreUrl == ""
}
