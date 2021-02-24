package com.iron.espresso.presentation.home.study

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

class StudyViewModel @ViewModelInject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>>
        get() = _categoryList

    fun getStudyCategory() {
        compositeDisposable += studyRepository
            .getStudyCategory()
            .networkSchedulers()
            .subscribe({
                _categoryList.value = it
                Logger.d("$it")
            }, {
                val errorResponse = it.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }

}