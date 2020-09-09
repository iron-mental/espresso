package com.iron.espresso.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroViewModel : ViewModel() {

    private val _clickTypeIdentifier =
        MutableLiveData<String>()
    val clickTypeIdentifier
        get() = _clickTypeIdentifier


    fun startSignUp() {
        _clickTypeIdentifier.value = TYPE_SIGN_UP
    }

    fun startSignIn() {
        _clickTypeIdentifier.value = TYPE_SIGN_IN
    }

    companion object {
        const val TYPE_SIGN_UP = "signUp"
        const val TYPE_SIGN_IN = "signIn"

    }
}