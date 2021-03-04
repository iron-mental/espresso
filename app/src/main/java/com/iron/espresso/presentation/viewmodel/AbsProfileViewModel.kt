package com.iron.espresso.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.User
import com.iron.espresso.ext.Event
import com.iron.espresso.presentation.profile.ProjectItem

enum class ProfileSns {
    GITHUB, GITHUB_REPO, LINKED_IN, WEB, APP_STORE, PLAY_STORE,
}

abstract class AbsProfileViewModel :
    BaseViewModel() {

    protected val _projectItemList =
        MutableLiveData(listOf(ProjectItem(), ProjectItem(), ProjectItem()))
    val projectItemList: LiveData<List<ProjectItem>> get() = _projectItemList

    private val _showLinkEvent = MutableLiveData<Event<String>>()
    val showLinkEvent: LiveData<Event<String>> get() = _showLinkEvent

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
}