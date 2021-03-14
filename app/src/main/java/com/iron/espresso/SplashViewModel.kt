package com.iron.espresso

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.ErrorCode
import com.iron.espresso.model.source.remote.ReIssuanceTokenRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class SplashViewModel @ViewModelInject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val getUser: GetUser
) :
    BaseViewModel() {

    private val _userInfoResult = MutableLiveData<User>()
    val userInfoResult: LiveData<User> get() = _userInfoResult

    private val _failedEvent = MutableLiveData<Event<Unit>>()
    val failedEvent: LiveData<Event<Unit>> get() = _failedEvent

    fun checkTokenAndResult(bearerToken: String, userId: Int) {
        compositeDisposable += getUser(userId)
            .networkSchedulers()
            .subscribe({
                _userInfoResult.value = it
                Logger.d("$it")
            }, {
                val errorResponse = it.toErrorResponse()

                if (errorResponse != null && errorResponse.code == ErrorCode.JWT_EXPIRED) {
                    reIssuanceAccessToken(bearerToken, AuthHolder.refreshToken)
                } else {
                    _failedEvent.value = Event(Unit)
                }

                Logger.d("$it")
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
                _failedEvent.value = Event(Unit)
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