package com.iron.espresso.presentation.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.ModifyUserCareer
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCareerViewModel @Inject constructor(private val modifyUserCareer: ModifyUserCareer) :
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

        compositeDisposable += modifyUserCareer(title, contents)
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