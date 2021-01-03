package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel

class EditProfileHeaderViewModel @ViewModelInject constructor() :
    BaseViewModel() {

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private val _introduce = MutableLiveData<String>()
    val introduce: LiveData<String> get() = _introduce

}