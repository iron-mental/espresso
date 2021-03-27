package com.iron.espresso.presentation.profile.edit

import android.net.Uri
import androidx.core.net.toFile
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.ModifyUserImage
import com.iron.espresso.domain.usecase.ModifyUserInfo
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import io.reactivex.rxjava3.core.Single

class EditProfileHeaderViewModel @ViewModelInject constructor(
    private val modifyUserImage: ModifyUserImage,
    private val modifyUserInfo: ModifyUserInfo
) :
    BaseViewModel() {

    private var initNickName = ""
    private var initIntroduce = ""
    val nickname = MutableLiveData<String>()
    val introduce = MutableLiveData<String>()

    private val _successEvent = MutableLiveData<Event<Boolean>>()
    val successEvent: LiveData<Event<Boolean>> get() = _successEvent

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

            val modifyJobList = mutableListOf<Single<Boolean>>()

            if (imageFile != null) {
                modifyJobList += modifyUserImage(
                    imageFile
                )
            }

            if (initNickName != nickname
                || initIntroduce != introduce
            ) {
                modifyJobList += modifyUserInfo(
                    if (initNickName != nickname) nickname else null,
                    introduce
                )
            }

            compositeDisposable += Single.zip(modifyJobList) { arr ->
                arr.map { it as Boolean }
            }
                .map { it.find { it } }
                .networkSchedulers()
                .subscribe({ isSuccess ->
                    if (isSuccess == true) {
                        _successEvent.value = Event(true)
                    }
                }, {
                    Logger.d("$it")
                    if (it is NoSuchElementException) {
                        _successEvent.value = Event(false)
                        return@subscribe
                    }
                    it.toErrorResponse()?.let { errorResponse ->
                        if (!errorResponse.message.isNullOrEmpty()) {
                            _toastMessage.value = Event(errorResponse.message)
                        }
                    }
                })
        }
    }
}