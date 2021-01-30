package com.iron.espresso.presentation.profile.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.Address
import com.iron.espresso.domain.usecase.GetAddressList
import com.iron.espresso.domain.usecase.ModifyUserLocation
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse

enum class PickStep {
    STEP_1, STEP_2, DONE
}

class EditAreaViewModel @ViewModelInject constructor(
    private val getAddressList: GetAddressList,
    private val modifyUserLocation: ModifyUserLocation,
) : BaseViewModel() {

    private val addressResponseList = mutableListOf<Address>()

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
        compositeDisposable += getAddressList()
            .networkSchedulers()
            .subscribe({
                addressResponseList.addAll(it)
                setStep1List()
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
            compositeDisposable += modifyUserLocation(addressStep1, addressStep2)
                .networkSchedulers()
                .subscribe({
                    _successEvent.value = Event(true)
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







