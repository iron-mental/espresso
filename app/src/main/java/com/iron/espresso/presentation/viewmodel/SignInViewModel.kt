package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.response.user.UserAuthResponse

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

    private val _loginResult = MutableLiveData<UserAuthResponse>()
    val loginResult: LiveData<UserAuthResponse> get() = _loginResult

    fun checkLogin(userId: String, userPass: String) {
        compositeDisposable += loginUser(userId, userPass, "나중에 넣기")
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    _loginResult.value = it.data
                    _checkType.value = CheckType.CHECK_ALL_SUCCESS
                } else {
                    _toastMessage.value = Event(it.message.orEmpty())
                    _checkType.value = CheckType.CHECK_ALL_FAIL
                }
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
}