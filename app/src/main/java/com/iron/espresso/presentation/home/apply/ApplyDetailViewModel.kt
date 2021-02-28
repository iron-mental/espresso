package com.iron.espresso.presentation.home.apply

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.DeleteApply
import com.iron.espresso.domain.usecase.ModifyApply
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign

class ApplyDetailViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val modifyApply: ModifyApply,
    private val deleteApply: DeleteApply
) : BaseViewModel() {

    private val applyStudyItem: ApplyStudyItem? by lazy {
        savedState.get(ApplyDetailFragment.ARG_APPLY)
    }

    private val _successEvent = MutableLiveData<Event<String>>()
    val successEvent: LiveData<Event<String>>
        get() = _successEvent


    fun requestModify(message: String) {
        val studyId = applyStudyItem?.studyId ?: -1
        val applyId = applyStudyItem?.id ?: -1

        if (studyId == -1 || applyId == -1) return

        compositeDisposable += modifyApply(
            studyId = studyId,
            applyId = applyId,
            message = message
        )
            .networkSchedulers()
            .subscribe({ (isSuccess, message) ->
                if (isSuccess) {
                    _successEvent.value = Event(message)
                }
            }, {
                Logger.d("$it")
            })
    }

    fun requestDelete() {
        val studyId = applyStudyItem?.studyId ?: -1
        val applyId = applyStudyItem?.id ?: -1

        if (studyId == -1 || applyId == -1) return

        compositeDisposable += deleteApply(
            studyId = studyId,
            applyId = applyId
        )
            .networkSchedulers()
            .subscribe({ (isSuccess, message) ->
                if (isSuccess) {
                    _successEvent.value = Event(message)
                }
            }, {
                Logger.d("$it")
            })
    }
}