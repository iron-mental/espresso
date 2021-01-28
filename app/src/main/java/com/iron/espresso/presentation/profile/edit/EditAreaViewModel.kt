package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.address.AddressResponse
import com.iron.espresso.model.source.remote.ModifyUserLocationRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

enum class PickStep {
    STEP_1, STEP_2, DONE
}

class EditAreaViewModel @ViewModelInject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val kakaoApi: KakaoApi
) : BaseViewModel() {

    private val addressResponseList = mutableListOf<AddressResponse>()

    private val _addressList = MutableLiveData<List<String>>()
    val addressList: LiveData<List<String>> get() = _addressList

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

    val step1 = MutableLiveData<String>()
    val step2 = MutableLiveData<String>()

    val pickStep: PickStep
        get() {
            return when {
                step2.value.isNullOrEmpty() && step1.value.isNullOrEmpty() -> PickStep.STEP_1
                step2.value.isNullOrEmpty() -> PickStep.STEP_2
                else -> PickStep.DONE
            }
        }

    init {
        compositeDisposable += remoteDataSource.getAddressList(AuthHolder.bearerToken)
            .networkSchedulers()
            .subscribe({
                if (it.result && it.data != null) {
                    addressResponseList.addAll(it.data)
                    setStep1List()
                }
            }, {

            })
    }

    fun setStep1List() {
        _addressList.value = addressResponseList.mapNotNull { it.sido }
    }

    fun setStep2List(step1Address: String) {
        val step2List = addressResponseList.find { it.sido == step1Address }?.siGungu
        if (step2List != null) {
            _addressList.value = step2List
        }
    }

    fun modifyInfo() {
        val addressStep1 = step1.value.orEmpty()
        val addressStep2 = step2.value.orEmpty()
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (
            addressStep1.isNotEmpty()
            && addressStep2.isNotEmpty()
            && bearerToken.isNotEmpty()
            && id != -1
        ) {
            val addressName = "$addressStep1 $addressStep2"

            compositeDisposable +=
                kakaoApi.getAddress(query = addressName)
                    .flatMap {
                        val address = it.addressResponses?.firstOrNull() ?: error("Address Response null")
                        val latitude = address.y?.toDoubleOrNull() ?: error("Address Response latitude null")
                        val longitude = address.x?.toDoubleOrNull() ?: error("Address Response longitude null")

                        remoteDataSource.modifyUserLocation(
                            bearerToken,
                            id,
                            ModifyUserLocationRequest(
                                latitude = latitude,
                                longitude = longitude,
                                sido = addressStep1,
                                sigungu = addressStep2
                            )
                        )
                    }
                    .networkSchedulers()
                    .subscribe({
                        if (it.result) {
                            _successEvent.value = Event(true)
                        } else if (!it.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(it.message)
                        }
                    }, {
                        Logger.d("$it")
                        it.toErrorResponse()?.let { errorResponse ->
                            if (!errorResponse.message.isNullOrEmpty()) {
                                _toastMessage.value = Event(errorResponse.message)
                            }
                        }
                    })
        }
    }
}







