package com.iron.espresso

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.Label
import com.iron.espresso.model.response.user.UserResponse
import com.iron.espresso.model.source.remote.ReIssuanceTokenRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import retrofit2.HttpException

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

                val errorResponse = (it as? HttpException)?.toErrorResponse()

                if (errorResponse != null && errorResponse.label == Label.JWT_EXPIRED.value) {
                    reIssuanceAccessToken(bearerToken, AuthHolder.refreshToken)
                }
            })
    }

    private fun reIssuanceAccessToken(bearerToken: String, refreshToken: String) {
        compositeDisposable += userRemoteDataSource.reIssuanceAccessToken(
            bearerToken, ReIssuanceTokenRequest(refreshToken)
        )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                 if (it.result) {
                     val newToken = it.data?.accessToken

                     if (newToken != null) {
                         saveNewAccessToken(newToken)
                     }
                 }
            }, {
                Logger.d("$it")
            })
    }

    private fun saveNewAccessToken(newAccessToken: String) {
        if (AuthHolder.updateAccessToken(newAccessToken)) {
            val bearerToken = AuthHolder.bearerToken
            val userId = AuthHolder.id
            if (bearerToken.isNotEmpty() && userId != null) {
                checkTokenAndResult(bearerToken, userId)
            }
        }
    }
}