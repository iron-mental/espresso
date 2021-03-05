package com.iron.espresso.data.model

enum class AuthorityType(val authority: String) {
    HOST("host"),
    MEMBER("member"),
    APPLIER("applier"),
    REJECT("reject"),
    NONE("none")
}