package com.iron.espresso.presentation.home.mystudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.study.MyStudyResponse
import retrofit2.HttpException

class MyStudyViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _studyList =
        MutableLiveData<List<MyStudyResponse>>()
    val studyList: LiveData<List<MyStudyResponse>>
        get() = _studyList

    fun showMyStudyList() {
        compositeDisposable += studyApi
            .getMyStudyList(
                bearerToken = AuthHolder.bearerToken,
                userId = AuthHolder.id ?: -1
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyList.value = it.data
                }
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
            })
    }
}