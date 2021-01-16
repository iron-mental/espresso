package com.iron.espresso.presentation.profile.edit

import android.net.Uri
import androidx.core.net.toFile
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
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.source.remote.ModifyUserImageRequest
import com.iron.espresso.model.source.remote.ModifyUserInfoRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import io.reactivex.Single

class EditProfileHeaderViewModel @ViewModelInject constructor(private val remoteDataSource: UserRemoteDataSource) :
    BaseViewModel() {

    private var initNickName = ""
    private var initIntroduce = ""
    val nickname = MutableLiveData<String>()
    val introduce = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> get() = _successEvent

    private var profileImage: Uri? = null

    fun setImageUri(imageUri: Uri) {
        profileImage = imageUri
    }

    fun initProfileData(nickname: String, introduce: String) {
        initNickName = nickname
        initIntroduce = introduce
        this.nickname.value = nickname
        this.introduce.value = introduce
    }

    fun modifyInfo() {
        if (nickname.value.isNullOrEmpty()) {
            _toastMessage.value = Event("이름은 비워둘 수 없습니다.")
            return
        }

        val imageFile = profileImage?.toFile()
        val nickname = nickname.value.orEmpty()
        val introduce = introduce.value.orEmpty()
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (nickname.isNotEmpty()
            && bearerToken.isNotEmpty()
            && id != -1
        ) {

            val modifyJobList = mutableListOf<Single<BaseResponse<Nothing>>>()

            if (imageFile != null) {
                modifyJobList += remoteDataSource.modifyUserImage(
                    bearerToken,
                    id,
                    ModifyUserImageRequest(imageFile)
                )
            }

            if (initNickName != nickname
                && initIntroduce != introduce) {
                modifyJobList += remoteDataSource.modifyUserInfo(
                    bearerToken,
                    id,
                    ModifyUserInfoRequest(
                        if (initNickName != nickname) nickname else null,
                        introduce
                    )
                )
            }

            compositeDisposable += Single.zip(modifyJobList) { arr ->
                arr.map {
                    it as BaseResponse<*>
                }
            }
                .networkSchedulers()
                .subscribe({ responseList ->
                    Logger.d("$responseList")
                    val isSuccess = responseList.firstOrNull { it.result } != null
                    if (isSuccess) {
                        _successEvent.value = Event(Unit)
                    } else {
                        val failedMessage = responseList.firstOrNull { it.result }?.message
                        if (!failedMessage.isNullOrEmpty()) {
                            _toastMessage.value = Event(failedMessage)
                        }
                    }

                }, {
                    it.toErrorResponse()?.let { errorResponse ->
                        Logger.d("$errorResponse")
                        if (!errorResponse.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(errorResponse.message)
                        }
                    }
                })
        }
    }
}