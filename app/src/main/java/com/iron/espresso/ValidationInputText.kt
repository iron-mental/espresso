package com.iron.espresso

import javax.annotation.Resource

enum class ValidationInputText(@Resource val resId: Int) {
    EMPTY_TITLE(R.string.empty_title),
    EMPTY_CONTENTS(R.string.empty_contents),
    EMPTY_INTRODUCE(R.string.empty_introduce),
    EMPTY_PROGRESS(R.string.empty_progress),
    EMPTY_TIME(R.string.empty_time),
    EMPTY_PLACE(R.string.empty_place),
    REGISTER_STUDY(R.string.register_study),
    REGISTER_NOTICE(R.string.register_notice),
    MODIFY_NOTICE(R.string.modify_notice),

    SUCCESS(R.string.success)
}