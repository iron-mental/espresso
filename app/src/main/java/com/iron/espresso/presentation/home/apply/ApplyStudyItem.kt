package com.iron.espresso.presentation.home.apply

import android.os.Parcelable
import com.iron.espresso.domain.entity.Apply
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApplyStudyItem(
    val id: Int,
    val userId: Int,
    val image: String,
    val message: String,
    val title: String
) : Parcelable {

    companion object {
        fun of(apply: Apply): ApplyStudyItem =
            ApplyStudyItem(apply.id, apply.userId, apply.image, apply.message, apply.title)
    }
}