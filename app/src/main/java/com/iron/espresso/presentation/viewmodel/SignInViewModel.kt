package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.response.user.UserAuthResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class SignInViewModel @ViewModelInject constructor(
    private val loginUser: LoginUser,
    private val userRemoteDataSource: UserRemoteDataSource
) :
    BaseViewModel() {

    val signInEmail = MutableLiveData<String>()
    val signInPassword = MutableLiveData<String>()
    val isProgressVisible = MutableLiveData<Boolean>()

    val checkEmail: (email: String) -> Unit = this::verifyEmailCheck
    val checkPassword: (email: String) -> Unit = this::verifyPasswordCheck

    private val _checkType = MutableLiveData<CheckType>()
    val checkType: LiveData<CheckType>
        get() = _checkType

    private val _userAuth = MutableLiveData<UserAuthResponse>()
    val userAuth: LiveData<UserAuthResponse> get() = _userAuth

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> get() = _userInfo

    fun checkLogin(userId: String, userPass: String) {
        compositeDisposable += loginUser(userId, userPass, "나중에 넣기")
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    _userAuth.value = it.data
                } else {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
            }, {
                Logger.d("$it")
            })
    }

    fun getUserInfo(bearerToken: String, userId: Int) {
        compositeDisposable += userRemoteDataSource.getUser(
            bearerToken,
            userId
        )
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    _userInfo.value = it.data?.toUser()
                } else {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
            }, {
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