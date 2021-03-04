package com.iron.espresso.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.domain.entity.User
import com.iron.espresso.domain.usecase.GetMyProjectList
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.profile.ProjectItem

class ProfileViewModel @ViewModelInject constructor(
    private val getUser: GetUser,
    private val getMyProjectList: GetMyProjectList
) : AbsProfileViewModel() {

    private val _refreshed = MutableLiveData<Event<Unit>>()
    val refreshed: LiveData<Event<Unit>> get() = _refreshed

    init {
        getProjectList()
    }

    private fun getProjectList() {
        compositeDisposable += getMyProjectList()
            .networkSchedulers()
            .subscribe({ projectList ->
                val list = projectList.map { ProjectItem.of(it) }.toMutableList()
                (list.size..3).forEach { position ->
                    projectItemList.value?.getOrNull(position - 1)?.let { item ->
                        list.add(item)
                    }
                }
                _projectItemList.value = list
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
            compositeDisposable += getUser(id)
                .networkSchedulers()
                .subscribe({ user ->
                    if (user != null) {
                        UserHolder.set(user)
                        setProfile(user)

                        _refreshed.value = Event(Unit)
                    }
                }, {
                    Logger.d("$it")
                })
        }
    }

    fun setProfile(user: User) {
        this.user.value = user
    }
}