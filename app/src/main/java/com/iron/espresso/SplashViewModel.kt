package com.iron.espresso

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.domain.usecase.GetVersionInfo
import com.iron.espresso.domain.usecase.ReIssuanceToken
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.ErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val reIssuanceToken: ReIssuanceToken,
    private val getUser: GetUser,
    private val getVersionInfo: GetVersionInfo
) :
    BaseViewModel() {

    enum class UpdateType {
        OPTIONAL,
        REQUIRED,
        SERVER_CLOSED
    }

    private val _userInfoResult = MutableLiveData<User>()
    val userInfoResult: LiveData<User> get() = _userInfoResult

    private val _failedEvent = MutableLiveData<Event<Unit>>()
    val failedEvent: LiveData<Event<Unit>> get() = _failedEvent

    private val _updateEvent = MutableLiveData<Event<UpdateType>>()
    val updateEvent: LiveData<Event<UpdateType>> get() = _updateEvent

    init {
        checkUpdate()
    }

    private fun checkUpdate() {
        getVersionInfo(BuildConfig.VERSION_NAME)
            .networkSchedulers()
            .subscribe({ version ->
                when {
                    version.maintenance == true -> {
                        _updateEvent.value = Event(UpdateType.SERVER_CLOSED)
                    }
                    version.force == 1 -> {
                        _updateEvent.value = Event(UpdateType.OPTIONAL)
                    }
                    version.force == 2 -> {
                        _updateEvent.value = Event(UpdateType.REQUIRED)
                    }
                    else -> {
                        val userId = AuthHolder.requireId()
                        if (userId != -1) {
                            checkTokenAndResult(userId)
                        } else {
                            _failedEvent.value = Event(Unit)
                        }
                    }
                }

            }, {
                val errorResponse = it.toErrorResponse()

                if (errorResponse != null && errorResponse.code == ErrorCode.JWT_EXPIRED) {
                    reIssuanceAccessToken(AuthHolder.refreshToken)
                } else {
                    _failedEvent.value = Event(Unit)
                }

                Logger.d("$it")
            })
    }

    fun checkTokenAndResult(userId: Int) {
        compositeDisposable += getUser(userId)
            .networkSchedulers()
            .subscribe({
                _userInfoResult.value = it
            }, {
                val errorResponse = it.toErrorResponse()

                if (errorResponse != null && errorResponse.code == ErrorCode.JWT_EXPIRED) {
                    reIssuanceAccessToken(AuthHolder.refreshToken)
                } else {
                    _failedEvent.value = Event(Unit)
                }

                Logger.d("$errorResponse")
            })
    }

    private fun reIssuanceAccessToken(refreshToken: String) {
        compositeDisposable += reIssuanceToken(
            refreshToken
        )
            .networkSchedulers()
            .subscribe({
                val newToken = it.accessToken

                if (newToken.isNotEmpty()) {
                    saveNewAccessToken(newToken)
                }
            }, {
                _failedEvent.value = Event(Unit)
                Logger.d("$it")
            })
    }

    private fun saveNewAccessToken(newAccessToken: String) {
        if (AuthHolder.updateAccessToken(newAccessToken)) {
            val userId = AuthHolder.requireId()
            if (userId != -1) {
                checkTokenAndResult(userId)
            }
        }
    }
}