package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.api.ProjectApi
import com.iron.espresso.model.response.project.ProjectListResponse
import com.iron.espresso.model.source.remote.UserRemoteDataSource
import com.iron.espresso.presentation.profile.ProjectItem

enum class ProfileSns {
    GITHUB, LINKED_IN, WEB, APP_STORE, PLAY_STORE
}

class ProfileViewModel @ViewModelInject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val projectApi: ProjectApi
) :
    BaseViewModel() {

    val user = MutableLiveData<User>()

    val clickSns: (sns: ProfileSns, url: String) -> Unit = { sns, url ->
        Logger.d("$url")
        val snsLink = if (url.isEmpty()) {
            when (sns) {
                ProfileSns.GITHUB -> user.value?.snsGithub
                ProfileSns.LINKED_IN -> user.value?.snsLinkedin
                ProfileSns.WEB -> user.value?.snsWeb
                else -> ""
            }
        } else {
            url
        }

        if (!snsLink.isNullOrEmpty()) {
            _showLinkEvent.value = Event(snsLink)
        }
    }

    private val _showLinkEvent = MutableLiveData<Event<String>>()
    val showLinkEvent: LiveData<Event<String>> get() = _showLinkEvent

    private val _projectItemList = MutableLiveData<List<ProjectItem>>()
    val projectItemList: LiveData<List<ProjectItem>> get() = _projectItemList

    init {
        getProjectList()
    }

    private fun getProjectList() {
        val id = AuthHolder.id ?: return

        compositeDisposable += projectApi.getProjectList(AuthHolder.bearerToken, id)
            .networkSchedulers()
            .subscribe({ response ->
                Logger.d("$response")
                if (response.result) {
                    response.data?.let { projectListResponse: ProjectListResponse ->
//                        _projectItemList.value = projectListResponse.map { it.toProjectItem() }
                    }
                }
            }, {
                Logger.d("$it")
            })
    }

    fun refreshProfile() {
        val bearerToken = AuthHolder.bearerToken
        val id = AuthHolder.id ?: return

        if (bearerToken.isNotEmpty()
            && id != -1
        ) {

            compositeDisposable += userRemoteDataSource.getUser(
                bearerToken, id
            )
                .networkSchedulers()
                .subscribe({
                    if (it.result) {

                        val user = it.data?.toUser()

                        if (user != null) {
                            UserHolder.set(user)
                            setProfile(user)
                        }
                    }
                }, {

                })
        }
    }

    fun setProfile(user: User) {
        this.user.value = user
    }
}




