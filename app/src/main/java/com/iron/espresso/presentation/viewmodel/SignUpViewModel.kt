package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.CheckDuplicateEmail
import com.iron.espresso.domain.usecase.CheckDuplicateNickname
import com.iron.espresso.domain.usecase.RegisterUser
import com.iron.espresso.ext.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpViewModel @ViewModelInject constructor(
    private val registerUser: RegisterUser,
    private val checkDuplicateEmail: CheckDuplicateEmail,
    private val checkDuplicateNickname: CheckDuplicateNickname
) :
    BaseViewModel() {

    val signUpEmail = MutableLiveData<String>()
    val signUpNickname = MutableLiveData<String>()
    val signUpPassword = MutableLiveData<String>()
    val isProgressVisible = MutableLiveData<Boolean>()

    private val _checkType = MutableLiveData<CheckType>()
    val checkType: LiveData<CheckType>
        get() = _checkType

    private val _exitIdentifier = MutableLiveData<Boolean>()
    val exitIdentifier: LiveData<Boolean>
        get() = _exitIdentifier


    val checkEmail: (email: String) -> Unit = this::verifyEmailCheck
    val checkNickname: (email: String) -> Unit = this::verifyNicknameCheck
    val checkPassword: (email: String) -> Unit = this::verifyPasswordCheck

    fun verifyEmailCheck(email: String?) {
        email?.let {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                compositeDisposable += checkDuplicateEmail.invoke(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ message ->
                        if (message.result) {
                            _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
                        } else {
                            _checkType.value = CheckType.CHECK_EMAIL_FAIL
                        }
                        Logger.d(it)
                    }, {
                        _checkType.value = CheckType.CHECK_EMAIL_FAIL
                        Logger.d("$it")
                    })
            } else {
                _checkType.value = CheckType.CHECK_EMAIL_FAIL
            }
        }
    }

    fun verifyNicknameCheck(nickname: String?) {
        nickname?.let {
            if(nickname.length in 2..7){
                compositeDisposable += checkDuplicateNickname.invoke(nickname)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ message ->
                        Logger.d("${message.message.length}")
                        if (message.result) {
                            _checkType.value = CheckType.CHECK_NICKNAME_SUCCESS
                        } else {
                            _checkType.value = CheckType.CHECK_NICKNAME_FAIL
                        }
                    }, {
                        _checkType.value = CheckType.CHECK_NICKNAME_FAIL
                    })
            }else{
                _checkType.value = CheckType.CHECK_NICKNAME_FAIL
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

    fun registerUser(userId: String, userPass: String, nickname: String) {
        compositeDisposable += registerUser.invoke(userId, userPass, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("$it")
                _checkType.value = CheckType.CHECK_ALL_SUCCESS
            }, {
                Logger.d("$it")
            })
    }

    fun exitViewModel() {
        _exitIdentifier.value = true
    }

    companion object {
        const val EMPTY = ""
    }
}

enum class CheckType {
    CHECK_EMAIL_SUCCESS, CHECK_NICKNAME_SUCCESS, CHECK_PASSWORD_SUCCESS, CHECK_ALL_SUCCESS,
    CHECK_EMAIL_FAIL, CHECK_NICKNAME_FAIL, CHECK_PASSWORD_FAIL, CHECK_ALL_FAIL
}