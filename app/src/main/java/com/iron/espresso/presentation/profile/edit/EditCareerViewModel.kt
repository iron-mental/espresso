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
import com.iron.espresso.model.source.remote.ModifyUserCareerRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class EditCareerViewModel @ViewModelInject constructor(private val remoteDataSource: UserRemoteDataSource) :
    BaseViewModel() {

    private var initTitle = ""
    private var initContents = ""
    val title = MutableLiveData<String>()
    val contents = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    fun initProfileData(title: String, contents: String) {
        initTitle = title
        initContents = contents
        this.title.value = title
        this.contents.value = contents
    }

    fun modifyInfo() {
        if (title.value == initTitle && contents.value == initContents) {
            _successEvent.value = Event(false)
            return
        }

        val title = title.value.orEmpty()
        val contents = contents.value.orEmpty()
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (bearerToken.isNotEmpty() && id != -1) {
            compositeDisposable += remoteDataSource.modifyUserCareer(
                bearerToken,
                id,
                ModifyUserCareerRequest(title, contents)
            )
                .networkSchedulers()
                .subscribe({
                    if (it.result) {
                        _successEvent.value = Event(true)
                    } else if (!it.message.isNullOrEmpty()) {
                        _toastMessage.value = Event(it.message)
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