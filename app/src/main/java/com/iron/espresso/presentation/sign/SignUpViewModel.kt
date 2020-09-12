package com.iron.espresso.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    val signUpEmail = MutableLiveData<String>()
    val signUpNickname = MutableLiveData<String>()
    val signUpPassword = MutableLiveData<String>()

    private val _checkType = MutableLiveData<CheckType>()
    val checkType
        get() = _checkType


    val checkEmail: Function1<String, Unit> = this::verifyEmailCheck
    val checkNickname: Function1<String, Unit> = this::verifyNicknameCheck
    val checkPassword: Function1<String, Unit> = this::verifyPasswordCheck

    fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                _checkType.value = CheckType.CHECK_NICKNAME
        }
    }

    fun verifyNicknameCheck(nickname: String?) {
        nickname?.let {
            _checkType.value = CheckType.CHECK_PASSWORD
        }
    }

    fun verifyPasswordCheck(password: String?) {
        password?.let {
            _checkType.value = CheckType.CHECK_ALL
        }
    }

    fun startViewModel() {
        signUpEmail.value = EMPTY
        signUpNickname.value = EMPTY
        signUpNickname.value = EMPTY
        _checkType.value = CheckType.CHECK_EMAIL
    }


    companion object {
        private const val EMPTY = ""
    }
}

enum class CheckType {
    CHECK_EMAIL, CHECK_NICKNAME, CHECK_PASSWORD, CHECK_ALL
}