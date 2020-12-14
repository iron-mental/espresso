package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.study.StudyListResponse
import retrofit2.HttpException

class StudyListViewModel : BaseViewModel() {

    private val _studyList = MutableLiveData<StudyListResponse>()
    val studyList: LiveData<StudyListResponse>
        get() = _studyList

    @SuppressLint("CheckResult")
    fun getStudyList() {
        ApiModule.provideStudyApi()
            .getStudyList(
                bearerToken = AuthHolder.bearerToken,
                category = "android",
                sort = "new"
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                _studyList.value = it.data
            }, {
                Logger.d("$it")
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("${errorResponse.message}")
                }
            })
    }

}