package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.presentation.profile.ProjectItem

class EditProjectViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _projectItemList = MutableLiveData<List<ProjectItem>>()
    val projectItemList: LiveData<List<ProjectItem>> get() = _projectItemList

    private val _firstItem = MutableLiveData<ProjectItem>()
    val firstItem: LiveData<ProjectItem> get() = _firstItem

    private val _secondItem = MutableLiveData<ProjectItem>()
    val secondItem: LiveData<ProjectItem> get() = _secondItem

    private val _thirdItem = MutableLiveData<ProjectItem>()
    val thirdItem: LiveData<ProjectItem> get() = _thirdItem

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
}