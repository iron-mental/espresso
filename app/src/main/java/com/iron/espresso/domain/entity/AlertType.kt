package com.iron.espresso.domain.entity

import com.google.gson.annotations.SerializedName

enum class AlertType(val emoji: String) {
    @SerializedName("chat")
    CHAT(""),
    @SerializedName("study_update")
    STUDY_UPDATE("✏️"),
    @SerializedName("study_delegate")
    STUDY_DELEGATE("🙋🏻"),
    @SerializedName("study_delete")
    STUDY_DELETE("‼️"),
    @SerializedName("apply_new")
    APPLY_NEW("✉️"),
    @SerializedName("apply_allow")
    APPLY_ALLOW("🎉"),
    @SerializedName("apply_reject")
    APPLY_REJECT("😭"),
    @SerializedName("notice_new")
    NOTICE_NEW("📌"),
    @SerializedName("notice_update")
    NOTICE_UPDATE("📌"),
    @SerializedName("push_test")
    PUSH_TEST("");

    companion object {
        fun getAlertType(event: String): AlertType? {
            return values().find { it.name == event.toUpperCase() }
        }
    }
}