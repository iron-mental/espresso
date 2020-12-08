package com.iron.espresso.presentation.home.mystudy

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.study.MyStudyResponse
import retrofit2.HttpException

class MyStudyViewModel : BaseViewModel() {

    private val _studyList =
        MutableLiveData<List<MyStudyResponse>>()
    val studyList: LiveData<List<MyStudyResponse>>
        get() = _studyList

    @SuppressLint("CheckResult")
    fun showMyStudyList() {
        ApiModule.provideStudyApi()
            .getMyStudyList(
                bearerToken = AuthHolder.bearerToken,
                userId = 19
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyList.value = it.data
                }
                Log.d("TAG", "성공 : $it")
            }, {
                val errorResponse = (it as HttpException).toErrorResponse()
                Logger.d("$errorResponse")
            })
    }

}