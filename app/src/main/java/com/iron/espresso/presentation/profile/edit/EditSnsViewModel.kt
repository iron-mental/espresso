package com.iron.espresso.presentation.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.ModifyUserSns
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditSnsViewModel @Inject constructor(
    private val modifyUserSns: ModifyUserSns
) :
    BaseViewModel() {

    private var initGithub = ""
    private var initLinkedIn = ""
    private var initWeb = ""
    val github = MutableLiveData<String>()
    val linkedIn = MutableLiveData<String>()
    val web = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    fun initProfileData(github: String, linkedIn: String, web: String) {
        initGithub = github
        initLinkedIn = linkedIn
        initWeb = web
        this.github.value = github
        this.linkedIn.value = linkedIn
        this.web.value = web
    }

    fun modifyInfo() {
        if (github.value == initGithub
            && linkedIn.value == initLinkedIn
            && web.value == initWeb
        ) {
            _successEvent.value = Event(false)
            return
        }

        val github = github.value.orEmpty()
        val linkedIn = linkedIn.value.orEmpty()
        val web = web.value.orEmpty()
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return


        if (bearerToken.isNotEmpty() && id != -1) {
            compositeDisposable += modifyUserSns(
                github, linkedIn, web
            )
                .networkSchedulers()
                .subscribe({
                    _successEvent.value = Event(true)
                }, {
                    Logger.d("$it")
                    it.toErrorResponse()?.let { errorResponse ->
                        Logger.d("$errorResponse")
                        if (!errorResponse.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(errorResponse.message)
                        }
                    }
                })
        }
    }
}