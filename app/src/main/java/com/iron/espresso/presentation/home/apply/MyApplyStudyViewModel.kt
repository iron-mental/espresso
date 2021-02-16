package com.iron.espresso.presentation.home.apply

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.GetMyApplyList
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign

class MyApplyStudyViewModel @ViewModelInject constructor(private val getMyApplyList: GetMyApplyList) :
    BaseViewModel() {

    private val _applyList = MutableLiveData<List<MyApplyStudyItem>>()
    val applyList: LiveData<List<MyApplyStudyItem>>
        get() = _applyList

    init {
        getApplyList()
    }

    fun getApplyList() {
        compositeDisposable += getMyApplyList()
            .map {
                it.map { apply ->
                    MyApplyStudyItem.of(apply)
                }
            }
            .networkSchedulers()
            .subscribe({ list ->
                _applyList.value = list
            }, {
                Logger.d("$it")
            })
    }
}