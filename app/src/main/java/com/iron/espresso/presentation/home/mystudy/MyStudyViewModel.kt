package com.iron.espresso.presentation.home.mystudy

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.response.study.MyStudyListResponse
import com.iron.espresso.model.response.study.MyStudyResponse

class MyStudyViewModel : BaseViewModel() {

    private val _movieList =
        MutableLiveData<List<MyStudyResponse>>()
    val movieList: LiveData<List<MyStudyResponse>>
        get() = _movieList

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTksImVtYWlsIjoicmhkdWRja3NAbmF2ZXIuY29tIiwibmlja25hbWUiOiLqs6DsmIHssKwiLCJpYXQiOjE2MDU4NDk0MzMsImV4cCI6MTYwNzE0NTQzMywiaXNzIjoidGVybWluYWwtc2VydmVyIiwic3ViIjoidXNlckluZm8tYWNjZXNzIn0.Eptf9T9Z_c-VmIUqLNV5CKAN-ftm1sZSwOzs91SrIr0"


    fun showMyStudyList() {
        getMyStudyList { item ->
            _movieList.value = item
        }
    }

    @SuppressLint("CheckResult")
    private fun getMyStudyList(callback: (item: MyStudyListResponse) -> Unit) {
        ApiModule.provideStudyApi()
            .getMyStudyList(
                bearerToken = "Bearer $token",
                userId = 19
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    callback(it.data)
                }
                Log.d("TAG", "성공 : $it")
            }, {
                Log.d("TAG", "실패 : $it")
            })
    }

}