package com.iron.espresso.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    val signUpEmail = MutableLiveData<String>()
    val signUpNickname = MutableLiveData<String>()
    val signUpPassword = MutableLiveData<String>()
    val isProgressVisible = MutableLiveData<Boolean>()

    private val _checkType = MutableLiveData<CheckType>()
    val checkType
        get() = _checkType

    private val _exitIdentifier = MutableLiveData<Boolean>()
    val exitIdentifier
        get() = _exitIdentifier


    val checkEmail: Function1<String, Unit> = this::verifyEmailCheck
    val checkNickname: Function1<String, Unit> = this::verifyNicknameCheck
    val checkPassword: Function1<String, Unit> = this::verifyPasswordCheck

    fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_EMAIL_FAIL
            }
        }
    }

    fun verifyNicknameCheck(nickname: String?) {
        nickname?.let {
            _checkType.value = CheckType.CHECK_NICKNAME_SUCCESS
        }
    }

    fun verifyPasswordCheck(password: String?) {
        password?.let {
            if (password.length > 6) {
                _checkType.value = CheckType.CHECK_PASSWORD_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_PASSWORD_FAIL
            }
        }
    }

    fun registerUser() {
        checkType.value = CheckType.CHECK_ALL_SUCCESS
    }

    fun startViewModel() {
        signUpEmail.value = EMPTY
        signUpNickname.value = EMPTY
        signUpNickname.value = EMPTY
        _checkType.value = CheckType.CHECK_NULL
        _exitIdentifier.value = false
    }

    fun exitViewModel() {
        _exitIdentifier.value = true
    }

    companion object {
        private const val EMPTY = ""
    }
}

enum class CheckType {
    CHECK_NULL,
    CHECK_EMAIL_SUCCESS, CHECK_NICKNAME_SUCCESS, CHECK_PASSWORD_SUCCESS, CHECK_ALL_SUCCESS,
    CHECK_EMAIL_FAIL, CHECK_NICKNAME_FAIL, CHECK_PASSWORD_FAIL, CHECK_ALL_FAIL
}