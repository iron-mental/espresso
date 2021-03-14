package com.iron.espresso.presentation.home.setting

import com.iron.espresso.presentation.viewmodel.AbsProfileViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.domain.usecase.VerifyEmail
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

class SettingViewModel @ViewModelInject constructor(
    private val getUser: GetUser,
    private val verifyEmail: VerifyEmail
) : AbsProfileViewModel() {

    private val _refreshed = MutableLiveData<Event<Unit>>()
    val refreshed: LiveData<Event<Unit>> get() = _refreshed

    fun refreshProfile() {
        val id = AuthHolder.id ?: return

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

    fun setProfile(user: User) {
        this.user.value = user
    }

    fun emailVerify() {
        compositeDisposable += verifyEmail()
            .networkSchedulers()
            .subscribe({ (isSuccess, message) ->
                if (isSuccess) {
                    _toastMessage.value = Event(message)
                }
                Logger.d("{$isSuccess + $message}")
            }, {
                Logger.d("$it")
                it.toErrorResponse()?.let { response ->
                    _toastMessage.value = Event(response.message.orEmpty())
                }
            })
    }
}