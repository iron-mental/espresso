package com.iron.espresso.presentation.home.mystudy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.model.response.study.MyStudyResponse

class MyStudyViewModel : BaseViewModel() {

    private val _movieList =
        MutableLiveData<List<MyStudyResponse>>()
    val movieList: LiveData<List<MyStudyResponse>>
        get() = _movieList


    fun showMyStudyList() {
    }

}