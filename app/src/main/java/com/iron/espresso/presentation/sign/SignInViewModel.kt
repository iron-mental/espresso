package com.iron.espresso.presentation.sign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iron.espresso.domain.usecase.GetUser

class SignInViewModel(private val getUser: GetUser) : ViewModel() {

    val signInEmail = MutableLiveData<String>()
    val signInPassword = MutableLiveData<String>()
    val isProgressVisible = MutableLiveData<Boolean>()

    val checkEmail: (email: String) -> Unit = this::verifyEmailCheck
    val checkPassword: (email: String) -> Unit = this::verifyPasswordCheck

    private val _checkType = MutableLiveData<CheckType>()
    val checkType: LiveData<CheckType>
        get() = _checkType

    private val _exitIdentifier = MutableLiveData<Boolean>()
    val exitIdentifier: LiveData<Boolean>
        get() = _exitIdentifier


    fun checkLogin(userId: String, userPass: String) {
        Thread {
            val getUser = getUser(userId, userPass)
            getUser.email?.let { Log.d("결과", "성공") } ?: Log.d("결과", "실패")
        }.start()

    }


    fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_EMAIL_FAIL
            }
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

    fun verifyLogin(email: String?, password: String?) {
        if (email != null || password != null) {
            _checkType.value = CheckType.CHECK_ALL_SUCCESS
        }
    }

    fun exitViewModel() {
        _exitIdentifier.value = true
    }

}