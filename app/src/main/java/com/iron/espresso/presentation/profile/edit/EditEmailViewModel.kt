package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.ModifyUserEmail
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

class EditEmailViewModel @ViewModelInject constructor(
    private val modifyUserEmail: ModifyUserEmail
) :
    BaseViewModel() {

    private var initEmail = ""
    val email = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    fun initProfileData(email: String) {
        this.email.value = email
    }

    fun modifyInfo() {
        if (email.value == initEmail) {
            _successEvent.value = Event(false)
            return
        }

        val email = email.value.orEmpty()


        compositeDisposable += modifyUserEmail(email)
            .networkSchedulers()
            .subscribe({
                _successEvent.value = Event(true)
            }, {
                Logger.d("$it")
                it.toErrorResponse()?.let { errorResponse ->
                    if (!errorResponse.message.isNullOrEmpty()) {
                        _toastMessage.value = Event(errorResponse.message)
                    }
                }
            })
    }
}