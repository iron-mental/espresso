package com.iron.espresso.presentation.home.setting

import com.iron.espresso.presentation.viewmodel.AbsProfileViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.DeleteUser
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.domain.usecase.LogoutUser
import com.iron.espresso.domain.usecase.VerifyEmail
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

class SettingViewModel @ViewModelInject constructor(
    private val getUser: GetUser,
    private val verifyEmail: VerifyEmail,
    private val logoutUser: LogoutUser,
    private val deleteUser: DeleteUser
) : AbsProfileViewModel() {

    private val _refreshed = MutableLiveData<Event<Unit>>()
    val refreshed: LiveData<Event<Unit>> get() = _refreshed

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> get() = _successEvent


    fun refreshProfile() {
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (bearerToken.isNotEmpty()
            && id != -1
        ) {
            compositeDisposable += getUser(id)
                .networkSchedulers()
                .subscribe({ user ->
                    if (user != null) {
                        UserHolder.set(user)
                        setProfile(user)

                        _refreshed.value = Event(Unit)
                    }
                }, {
                    Logger.d("$it")
                })
        }
    }

    fun setProfile(user: User) {
        this.user.value = user
    }

    fun emailVerify() {
        compositeDisposable += verifyEmail()
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    if (it.message != null) {
                        _toastMessage.value = Event(it.message)
                    }
                }
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }

    fun logout() {
        compositeDisposable += logoutUser()
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    if (it.message != null) {
                        _toastMessage.value = Event(it.message)
                        _successEvent.value = Event(Unit)
                    }
                }
                Logger.d("$it")
            }, {
                Logger.d("$it")
                it.toErrorResponse()?.let { response ->
                    _toastMessage.value = Event(response.message.orEmpty())
                }
            })
    }

    fun membershipWithdrawal(email: String, password: String) {
        compositeDisposable += deleteUser(
            email = email,
            password = password,
        )
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    if (it.message != null) {
                        _toastMessage.value = Event(it.message)
                        _successEvent.value = Event(Unit)
                    }
                }
                Logger.d("$it")
            }, {
                Logger.d("$it")
                it.toErrorResponse()?.let { response ->
                    _toastMessage.value = Event(response.message.orEmpty())
                }
            })
    }
}