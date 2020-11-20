package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.sign.CheckType

class SignInViewModel @ViewModelInject constructor(private val loginUser: LoginUser) :
    BaseViewModel() {

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

        compositeDisposable += loginUser(userId, userPass)
            .networkSchedulers()
            .subscribe({
                _checkType.value = CheckType.CHECK_ALL_SUCCESS
                Logger.d("$it")
            }, {
                _checkType.value = CheckType.CHECK_ALL_FAIL
                Logger.d("$it")
            })
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


    fun exitViewModel() {
        _exitIdentifier.value = true
    }

}