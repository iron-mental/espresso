package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.Logger
import com.iron.espresso.domain.usecase.GetMyProjectList
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.presentation.profile.ProjectItem
import com.iron.espresso.presentation.viewmodel.AbsProfileViewModel

class MemberProfileViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val getUser: GetUser,
    private val getMyProjectList: GetMyProjectList
) : AbsProfileViewModel() {

    private val userId: Int by lazy {
        savedState.get(KEY_USER_ID) ?: -1
    }

    fun getMember() {
        compositeDisposable += getUser(userId)
            .networkSchedulers()
            .subscribe({
                user.value = it
            }, { throwable ->

                throwable.toErrorResponse()?.let {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
                Logger.d("$throwable")
            })
    }

    fun getMemberProject() {
        compositeDisposable += getMyProjectList(userId)
            .networkSchedulers()
            .subscribe({ projectList ->
                val list = projectList.map { ProjectItem.of(it) }.toMutableList()
                (list.size..3).forEach { position ->
                    projectItemList.value?.getOrNull(position - 1)?.let { item ->
                        list.add(item)
                    }
                }
                _projectItemList.value = list
            }, { throwable ->

                throwable.toErrorResponse()?.let {
                    _toastMessage.value = Event(it.message.orEmpty())
                }
                Logger.d("$throwable")
            })
    }

    companion object {
        const val KEY_USER_ID = "USER_ID"
    }
}
