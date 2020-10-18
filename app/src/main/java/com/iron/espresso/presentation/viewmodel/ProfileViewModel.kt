package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iron.espresso.Logger
import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.usecase.GetGithubUser
import com.iron.espresso.ext.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ProfileViewModel @ViewModelInject constructor(private val getGithubUser: GetGithubUser) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    private val githubId = MutableLiveData<String>()

    val avatarUrl = MutableLiveData<String>()

    private val githubIdSubject: PublishSubject<String> by lazy {
        PublishSubject.create()
    }

    init {
        githubId.observeForever {
            githubIdSubject.onNext(it)
        }

        compositeDisposable += githubIdSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .subscribe({ githubId ->
                getProfileImage(githubId)
            }, {
                Logger.d("$it")
            })
    }


    private fun getProfileImage(userId: String) {
        compositeDisposable += getGithubUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: GithubUser ->
                avatarUrl.value = response.avatarUrl
            }, {
                Logger.d("$it")
            })
    }

    companion object {
        private const val DEBOUNCE_TIME = 500L
    }
}




