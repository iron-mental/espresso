package com.iron.espresso.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.iron.espresso.*
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.*
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val loginUser: LoginUser,
    private val getUser: GetUser,
    private val checkDuplicateEmail: CheckDuplicateEmail,
    private val checkDuplicateNickname: CheckDuplicateNickname
) : BaseViewModel() {

    val signUpEmail = MutableLiveData<String>()
    val signUpNickname = MutableLiveData<String>()
    val signUpPassword = MutableLiveData<String>()
    val isProgressVisible = MutableLiveData<Boolean>()

    private val _checkType = MutableLiveData<CheckType>()
    val checkType: LiveData<CheckType>
        get() = _checkType

    val checkEmail: (email: String) -> Unit = this::verifyEmailCheck
    val checkNickname: (email: String) -> Unit = this::verifyNicknameCheck
    val checkPassword: (email: String) -> Unit = this::verifyPasswordCheck


    private val isValidEmail: (email: String) -> Boolean = { email ->
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val isValidNickName: (nickname: String) -> Boolean = { nickname ->
        nickname.length in 2..7
    }

    private val isValidPassWord: (nickname: String) -> Boolean = { password ->
        password.length in 6..20
    }


    fun verifyEmailCheck(email: String = signUpEmail.value.orEmpty()) {
        if (isValidEmail(email)) {
            compositeDisposable += checkDuplicateEmail.invoke(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isDuplicate ->
                    if (!isDuplicate) {
                        _checkType.value = CheckType.CHECK_EMAIL_SUCCESS
                    } else {
                        _checkType.value =
                            CheckType.CHECK_EMAIL_FAIL.setMessage(App.instance.getString(R.string.duplicated_email))
                    }
                }, {
                    _checkType.value =
                        CheckType.CHECK_EMAIL_FAIL.setMessage(it.toErrorResponse()?.message.orEmpty())
                    Logger.d("$it")
                })
        } else {
            _checkType.value =
                CheckType.CHECK_EMAIL_FAIL.setMessage(App.instance.getString(R.string.invalid_email))
        }
    }

    fun verifyNicknameCheck(nickname: String = signUpNickname.value.orEmpty()) {
        if (isValidNickName(nickname)) {
            compositeDisposable += checkDuplicateNickname.invoke(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (isSuccess, message) ->
                    if (isSuccess) {
                        _checkType.value = CheckType.CHECK_NICKNAME_SUCCESS
                    } else {
                        _checkType.value = CheckType.CHECK_NICKNAME_FAIL.setMessage(message)
                    }
                }, {
                    _checkType.value =
                        CheckType.CHECK_NICKNAME_FAIL.setMessage(it.toErrorResponse()?.message.orEmpty())
                })
        } else {
            _checkType.value =
                CheckType.CHECK_NICKNAME_FAIL.setMessage(App.instance.getString(R.string.invalid_nickname))
        }
    }

    fun verifyPasswordCheck(password: String = signUpPassword.value.orEmpty()) {
        if (isValidPassWord(password)) {
            registerUser(
                signUpEmail.value.orEmpty(),
                signUpPassword.value.orEmpty(),
                signUpNickname.value.orEmpty(),
            )
        } else {
            _checkType.value = CheckType.CHECK_PASSWORD_FAIL
        }
    }

    private fun registerUser(userId: String, userPass: String, nickname: String) {
        compositeDisposable += registerUser.invoke(userId, userPass, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                login(userId, userPass)
            }, {
                Logger.d("$it")
            })
    }

    private fun login(userId: String, userPass: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Logger.d("Fetching FCM registration token failed ${task.exception}")
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                if (token != null) {
                    loginUser(userId, userPass, token)
                        .flatMap {
                            AuthHolder.set(it.data ?: error("auth is null"))
                            val id = it.data.id ?: error("id is null")
                            getUser(id)
                        }
                        .networkSchedulers()
                        .subscribe({
                            UserHolder.set(it)
                            _checkType.value = CheckType.CHECK_ALL_SUCCESS
                        }, {
                            Logger.d("$it")
                        })
                }
            })
    }

}

enum class CheckType(var message: String = "") {
    CHECK_EMAIL_SUCCESS, CHECK_NICKNAME_SUCCESS, CHECK_PASSWORD_SUCCESS, CHECK_ALL_SUCCESS,
    CHECK_EMAIL_FAIL, CHECK_NICKNAME_FAIL, CHECK_PASSWORD_FAIL, CHECK_ALL_FAIL;

    fun setMessage(message: String): CheckType {
        this.message = message
        return this
    }
}