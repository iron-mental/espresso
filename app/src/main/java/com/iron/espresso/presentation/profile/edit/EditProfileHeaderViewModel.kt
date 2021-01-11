package com.iron.espresso.presentation.profile.edit

import android.net.Uri
import androidx.core.net.toFile
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.source.remote.ModifyUserImageRequest
import com.iron.espresso.model.source.remote.ModifyUserInfoRequest
import com.iron.espresso.model.source.remote.UserRemoteDataSource

class EditProfileHeaderViewModel @ViewModelInject constructor(private val remoteDataSource: UserRemoteDataSource) :
    BaseViewModel() {

    val nickname = MutableLiveData<String>()
    val introduce = MutableLiveData<String>()


    private var profileImage: Uri? = null

    fun setImageUri(imageUri: Uri) {
        profileImage = imageUri
    }

    fun modifyInfo() {
        val imageFile = profileImage?.toFile()
        val nickname = nickname.value.orEmpty()
        val introduce = introduce.value.orEmpty()
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (nickname.isNotEmpty()
            && bearerToken.isNotEmpty()
            && id != -1
        ) {

            if (imageFile != null) {
                compositeDisposable += remoteDataSource.modifyUserImage(
                    bearerToken,
                    id,
                    ModifyUserImageRequest(imageFile)
                )
                    .networkSchedulers()
                    .subscribe({
                        Logger.d("$it")
                    }, {
                        Logger.d("$it")
                    })
            }

            compositeDisposable += remoteDataSource.modifyUserInfo(
                bearerToken,
                id,
                ModifyUserInfoRequest(nickname, introduce)
            )
                .networkSchedulers()
                .subscribe({
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                })
        }
    }
}