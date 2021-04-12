package com.iron.espresso

import androidx.annotation.StringRes

enum class ValidationInputText(@StringRes val resId: Int) {
    EMPTY_TITLE(R.string.empty_title),
    EMPTY_CONTENTS(R.string.empty_contents),
    EMPTY_INTRODUCE(R.string.empty_introduce),
    EMPTY_PROGRESS(R.string.empty_progress),
    EMPTY_TIME(R.string.empty_time),
    EMPTY_PLACE(R.string.empty_place),
    REGISTER_STUDY(R.string.register_study),
    REGISTER_NOTICE(R.string.register_notice),
    MODIFY_NOTICE(R.string.modify_notice),

    LENGTH_TITLE(R.string.length_title),
    SNS_NOTION(R.string.address_sns_notion),
    SNS_EVERNOTE(R.string.address_sns_evernote),

    SUCCESS(R.string.success)
}