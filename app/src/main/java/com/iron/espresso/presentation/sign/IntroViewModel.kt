package com.iron.espresso.presentation.sign

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel

class IntroViewModel : @ViewModelInject BaseViewModel() {

    private val _clickTypeIdentifier =
        MutableLiveData<SignType>()
    val clickTypeIdentifier: LiveData<SignType>
        get() = _clickTypeIdentifier


    fun startSignUp() {
        _clickTypeIdentifier.value = SignType.TYPE_SIGN_UP
    }

    fun startSignIn() {
        _clickTypeIdentifier.value = SignType.TYPE_SIGN_IN
    }

}

enum class SignType {
    TYPE_SIGN_UP, TYPE_SIGN_IN
}