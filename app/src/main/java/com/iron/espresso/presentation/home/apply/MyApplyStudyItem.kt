package com.iron.espresso.presentation.home.apply

import com.iron.espresso.domain.entity.Apply

data class MyApplyStudyItem(
    val id: Int,
    val userId: Int,
    val image: String,
    val message: String,
    val title: String
) {

    companion object {
        fun of(apply: Apply): MyApplyStudyItem =
            MyApplyStudyItem(apply.id, apply.userId, apply.image, apply.message, apply.title)
    }
}