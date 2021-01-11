package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.model.api.UserApi

class EditProfileHeaderViewModel @ViewModelInject constructor(private val api: UserApi) :
    BaseViewModel() {

    val nickname = MutableLiveData<String>()
    val introduce = MutableLiveData<String>()




}