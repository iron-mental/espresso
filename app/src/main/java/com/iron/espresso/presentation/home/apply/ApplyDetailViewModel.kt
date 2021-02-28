package com.iron.espresso.presentation.home.apply

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.Logger
import com.iron.espresso.domain.usecase.GetApplyOwner
import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.presentation.profile.ProjectItem
import com.iron.espresso.presentation.viewmodel.AbsProfileViewModel

class ApplyDetailViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val getApply: GetApplyOwner,
    private val getUser: GetUser
) : AbsProfileViewModel() {

    private val studyId: Int by lazy {
        savedState.get(KEY_STUDY_ID) ?: -1
    }

    private val applyId: Int by lazy {
        savedState.get(KEY_APPLY_ID) ?: -1
    }

    private val userId: Int by lazy {
        savedState.get(KEY_USER_ID) ?: -1
    }

    fun getApplyDetail() {
        if (studyId == -1 || applyId == -1) return

        compositeDisposable += getUser(userId)
            .map {
                user.postValue(it)
            }
            .flatMap {
                getApply(
                    studyId = studyId,
                    applyId = applyId
                )
            }
            .networkSchedulers()
            .subscribe({ applyDetail ->
                val list = applyDetail.projectList.map { ProjectItem.of(it) }.toMutableList()
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
        const val KEY_STUDY_ID = "STUDY_ID"
        const val KEY_APPLY_ID = "APPLY_ID"
        const val KEY_USER_ID = "USER_ID"
    }
}