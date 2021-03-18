package com.iron.espresso.presentation.home.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.DeleteUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

class DeleteUserViewModel @ViewModelInject constructor(private val deleteUser: DeleteUser) :
    BaseViewModel() {

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> get() = _successEvent

    fun membershipWithdrawal(email: String, password: String) {
        compositeDisposable += deleteUser(
            email = email,
            password = password,
        )
            .networkSchedulers()
            .subscribe({ (isSuccess, message) ->
                if (isSuccess) {
                    _toastMessage.value = Event(message)
                    _successEvent.value = Event(Unit)
                }
                Logger.d("$isSuccess $message")
            }, {
                Logger.d("$it")
                it.toErrorResponse()?.let { response ->
                    _toastMessage.value = Event(response.message.orEmpty())
                }
            })
    }
}