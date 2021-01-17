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
import com.iron.espresso.model.source.remote.ModifyUserSnsRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class EditSnsViewModel @ViewModelInject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
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
            compositeDisposable += userRemoteDataSource.modifyUserSns(
                bearerToken,
                id,
                ModifyUserSnsRequest(github, linkedIn, web)
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
                        // TODO : 형식 체크해주는 것 별개 처리 할건지 - BaseResponse(result=false, type=validation-error, label=sns_linkedin, message=유효하지 않은 주소입니다, data=null)
                        Logger.d("$errorResponse")
                        if (!errorResponse.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(errorResponse.message)
                        }
                    }
                })
        }
    }
}