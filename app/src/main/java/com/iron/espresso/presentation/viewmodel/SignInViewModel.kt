package com.iron.espresso.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val getUser: GetUser
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

    fun checkLogin(userId: String, userPass: String, pushToken: String) {
        showLoading()
        compositeDisposable += loginUser(userId, userPass, pushToken)
            .networkSchedulers()
            .subscribe({ response ->
                if (response.result) {
                    response.data?.let {
                        _userAuth.value = it
                    }

                } else {
                    _toastMessage.value = Event(response.message.orEmpty())
                }
            }, {
                Logger.d("$it")
                hideLoading()
            })
    }

    fun getUserInfo(userId: Int) {
        compositeDisposable += getUser(userId)
            .networkSchedulers()
            .subscribe({
                _userInfo.value = it
                hideLoading()
            }, { throwable ->
                Logger.d("$throwable")
                throwable.toErrorResponse()?.let {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
                hideLoading()
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
            if (password.length in 6..20) {
                _checkType.value = CheckType.CHECK_PASSWORD_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_PASSWORD_FAIL
            }
        }
    }
}