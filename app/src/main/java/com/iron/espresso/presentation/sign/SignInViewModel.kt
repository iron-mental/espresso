package com.iron.espresso.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    val signInEmail = MutableLiveData<String>()
    val signInPassword = MutableLiveData<String>()


    val checkEmail: Function1<String, Unit> = this::verifyEmailCheck
    val checkPassword: (email: String) -> Unit = this::verifyPasswordCheck

    private val _checkType = MutableLiveData<CheckType>()
    val checkType
        get() = _checkType

    private val _exitIdentifier = MutableLiveData<Boolean>()
    val exitIdentifier
        get() = _exitIdentifier


    private fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_EMAIL_FAIL
            }
        }
    }

    private fun verifyPasswordCheck(password: String?) {
        password?.let {
            if (password.length > 6) {
                _checkType.value = CheckType.CHECK_PASSWORD_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_PASSWORD_FAIL
            }
        }
    }

    fun verifyLogin(email: String?, password: String?) {
        if (email != null || password != null) {
            _checkType.value = CheckType.CHECK_ALL_SUCCESS
        }
    }

    fun startViewModel() {
        signInEmail.value = SignUpViewModel.EMPTY
        signInPassword.value = SignUpViewModel.EMPTY
        _checkType.value = CheckType.CHECK_NULL
        _exitIdentifier.value = false
    }

    fun exitViewModel() {
        _exitIdentifier.value = true
    }

}