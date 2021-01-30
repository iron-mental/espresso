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
import com.iron.espresso.model.api.ModifyProjectRequest
import com.iron.espresso.model.api.ProjectApi
import com.iron.espresso.presentation.profile.ProjectItem

class EditProjectViewModel @ViewModelInject constructor(
    private val projectApi: ProjectApi
) : BaseViewModel() {

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    private val _projectItemList = MutableLiveData<List<ProjectItem>>()
    val projectItemList: LiveData<List<ProjectItem>> get() = _projectItemList

    private val _firstItem = MutableLiveData<ProjectItem>()
    val firstItem: LiveData<ProjectItem> get() = _firstItem

    private val _secondItem = MutableLiveData<ProjectItem>()
    val secondItem: LiveData<ProjectItem> get() = _secondItem

    private val _thirdItem = MutableLiveData<ProjectItem>()
    val thirdItem: LiveData<ProjectItem> get() = _thirdItem

    fun initProjectList(list: List<ProjectItem>) {
        _projectItemList.value = list.filter { !it.isEmptyItem() }

        projectItemList.value?.forEachIndexed { index, item ->
            when (index) {
                0 -> {
                    _firstItem.value = item
                }
                1 -> {
                    _secondItem.value = item
                }
                2 -> {
                    _thirdItem.value = item
                }
            }
        }
    }

    fun addProject() {
        val item = ProjectItem()
        _projectItemList.value = projectItemList.value.orEmpty() + item
        when (projectItemList.value?.size) {
            1 -> {
                _firstItem.value = item
            }
            2 -> {
                _secondItem.value = item
            }
            3 -> {
                _thirdItem.value = item
            }
        }
    }

    fun modifyInfo(list: List<ProjectItem>) {
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        val modifyList = list.filterIndexed { index, projectItem ->
            projectItem != projectItemList.value?.getOrNull(index)
        }

        val modifyRequestList =  modifyList.map {
            ModifyProjectRequest.of(it)
        }

        if (bearerToken.isNotEmpty() && id != -1) {
            compositeDisposable += projectApi.registerProject(
                bearerToken,
                id,
                modifyRequestList
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
                        Logger.d("$errorResponse")
                        if (!errorResponse.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(errorResponse.message)
                        }
                    }
                })
        }
    }
}