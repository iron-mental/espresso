package com.iron.espresso.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.App
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.CheckDuplicateEmail
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.domain.usecase.LoginUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.user.UserAuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val getUser: GetUser,
    private val checkDuplicateEmail: CheckDuplicateEmail
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

    private val isValidEmail: (email: String) -> Boolean = { email ->
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private val isValidPassWord: (nickname: String) -> Boolean = { password ->
        password.length in 6..20
    }

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
                    _checkType.value = CheckType.CHECK_PASSWORD_FAIL.setMessage(response.message.orEmpty())
                }
            }, {
                val errorResponse = it.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("${errorResponse.message}")
                    _checkType.value = CheckType.CHECK_PASSWORD_FAIL.setMessage(errorResponse.message.orEmpty())
                }

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
        if (email != null) {
            if (isValidEmail(email)) {
                compositeDisposable += checkDuplicateEmail.invoke(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ isDuplicate ->
                        if (!isDuplicate) {
                            _checkType.value =
                                CheckType.CHECK_EMAIL_FAIL.setMessage(App.instance.getString(R.string.not_found_email))
                        } else {
                            _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
                        }
                    }, {
                        _checkType.value =
                            CheckType.CHECK_EMAIL_FAIL.setMessage(it.toErrorResponse()?.message.orEmpty())
                        Logger.d("$it")
                    })
            } else {
                _checkType.value = CheckType.CHECK_EMAIL_FAIL
            }
        }
    }

    fun verifyPasswordCheck(password: String?) {
        if (password != null) {
            if (isValidPassWord(password)) {
                _checkType.value = CheckType.CHECK_PASSWORD_SUCCESS
            } else {
                _checkType.value = CheckType.CHECK_PASSWORD_FAIL
            }
        }
    }
}