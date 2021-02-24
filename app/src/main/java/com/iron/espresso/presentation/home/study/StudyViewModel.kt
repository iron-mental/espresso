package com.iron.espresso.presentation.home.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign

class StudyViewModel : BaseViewModel() {

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>>
        get() = _categoryList

    fun getStudyCategory() {
        compositeDisposable += ApiModule.provideStudyApi()
            .getStudyCategory()
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _categoryList.value = it.data
                }
            }, {

            })
    }

}