package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iron.espresso.Logger
import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ProfileViewModel @ViewModelInject constructor(private val getUser: GetUser) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val githubId = MutableLiveData<String>()


    val avatarUrl = MutableLiveData<String>()

    private val githubIdSubject: PublishSubject<String> by lazy {
        PublishSubject.create()
    }

    init {
        githubId.observeForever {
            Logger.d("$it")
            githubIdSubject.onNext(it)
        }

        compositeDisposable += githubIdSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({ githubId ->
                Logger.d("$githubId")
                getProfileImage(githubId)
            }, {
                Logger.d("$it")
            })
    }


    fun getProfileImage(userId: String) {
        compositeDisposable += getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: GithubUser ->
                Logger.d("$response")
                avatarUrl.value = response.avatarUrl
            }, {
                Logger.d("$it")
            })
    }
}




