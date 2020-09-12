package com.iron.espresso.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    val signUpEmail = MutableLiveData<String>()

    val checkEmail: Function1<String, Unit> = this::verifyEmailCheck

    private val _checkType = MutableLiveData<CheckType>()
    val checkType
        get() = _checkType

    fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                _checkType.value = CheckType.CHECK_NICKNAME
        }
    }
}

enum class CheckType {
    CHECK_EMAIL, CHECK_NICKNAME, CHECK_PASSWORD, CHECK_ALL
}