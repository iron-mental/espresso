package com.iron.espresso.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iron.espresso.Logger
import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val getUser: GetUser) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val avatarUrl = MutableLiveData<String>()

    fun getProfileImage(userId: String) {
        compositeDisposable += getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: GithubUser ->
                avatarUrl.value = response.avatarUrl
            }, {
                Logger.d("$it")
            })

        // Activity -> ViewModel -> Repository(네트워크 상태에따라 로컬데이터일지 리모트 데이터일지 판별해서 요청) -> RemoteDataSource, LocalDataSource
    }
}
// domain - use case, entity




