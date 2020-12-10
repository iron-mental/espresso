package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.GithubUser
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetGithubUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.api.ProjectApi
import com.iron.espresso.model.response.project.ProjectListResponse
import com.iron.espresso.presentation.profile.ProjectItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

enum class ProfileSns {
    GITHUB, LINKED_IN, WEB
}

class ProfileViewModel @ViewModelInject constructor(
    private val getGithubUser: GetGithubUser,
    private val projectApi: ProjectApi
) :
    BaseViewModel() {

    val user = MutableLiveData<User>()
    private val githubId = MutableLiveData<String>()

    val avatarUrl = MutableLiveData<String>()

    val isEditMode = MutableLiveData(false)

    private val githubIdSubject: PublishSubject<String> by lazy {
        PublishSubject.create()
    }

    val clickSns: (sns: ProfileSns) -> Unit = {
        val snsLink = when (it) {
            ProfileSns.GITHUB -> user.value?.snsGithub
            ProfileSns.LINKED_IN -> user.value?.snsLinkedin
            ProfileSns.WEB -> user.value?.snsWeb
        }

        if (!snsLink.isNullOrEmpty()) {
            _showLinkEvent.value = Event(snsLink)
        }
    }

    private val _showLinkEvent = MutableLiveData<Event<String>>()
    val showLinkEvent: LiveData<Event<String>> get() = _showLinkEvent

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


        getProjectList()
    }

    private val _projectItemList = MutableLiveData<List<ProjectItem>>()
    val projectItemList: LiveData<List<ProjectItem>> get() = _projectItemList

    private fun getProjectList() {
        val id = AuthHolder.id ?: return
        compositeDisposable += projectApi.getProjectList(AuthHolder.bearerToken, id)
            .networkSchedulers()
            .subscribe({ response ->
                Logger.d("$response")
                if (response.result) {
                    response.data?.let { projectListResponse: ProjectListResponse ->
                        _projectItemList.value = projectListResponse.map { it.toProjectItem() }
                    }
                }
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


    fun setProfile(user: User) {
        this.user.value = user
    }

    fun enableEditMode() {
        isEditMode.value = true
    }

    companion object {
        private const val DEBOUNCE_TIME = 500L
    }
}




