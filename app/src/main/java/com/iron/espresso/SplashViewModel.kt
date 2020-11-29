package com.iron.espresso

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.response.user.UserResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class SplashViewModel @ViewModelInject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    BaseViewModel() {

    private val _userInfoResult = MutableLiveData<UserResponse>()
    val userInfoResult: LiveData<UserResponse> get() = _userInfoResult

    fun checkTokenAndResult(bearerToken: String, userId: Int) {
        compositeDisposable += userRemoteDataSource.getUser(bearerToken, userId)
            .networkSchedulers()
            .subscribe({
                if (it.result) {
                    _userInfoResult.value = it.data
                }
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }
}