package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.source.remote.ModifyUserEmailRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class EditEmailViewModel @ViewModelInject constructor(
    private val remoteDataSource: UserRemoteDataSource
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
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return


        if (bearerToken.isNotEmpty() && id != -1) {
            compositeDisposable += remoteDataSource.modifyUserEmail(
                bearerToken, id,
                ModifyUserEmailRequest(email)
            )
                .networkSchedulers()
                .subscribe({
                    if (it.result) {
                        _successEvent.value = Event(true)
                    } else {
                        val message = it.message
                        if (!message.isNullOrEmpty()) {
                            _toastMessage.value = Event(message)
                        }
                    }
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
}