package com.iron.espresso

import javax.annotation.Resource

enum class ValidationInputText(@Resource val resId: Int) {
    EMPTY_TITLE(R.string.empty_title),
    EMPTY_INTRODUCE(R.string.empty_introduce),
    EMPTY_PROGRESS(R.string.empty_progress),
    EMPTY_TIME(R.string.empty_time),
    EMPTY_PLACE(R.string.empty_place),
    SUCCESS(R.string.success_register)
}