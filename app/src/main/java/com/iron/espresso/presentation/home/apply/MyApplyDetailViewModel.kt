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

class MyApplyDetailViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val modifyApply: ModifyApply,
    private val deleteApply: DeleteApply
) : BaseViewModel() {

    private val applyStudyItem: ApplyStudyItem? by lazy {
        savedState.get(MyApplyDetailFragment.ARG_APPLY)
    }

    private val _modifiedEvent = MutableLiveData<Event<Unit>>()
    val modifiedEvent: LiveData<Event<Unit>>
        get() = _modifiedEvent

    private val _deletedEvent = MutableLiveData<Event<Unit>>()
    val deletedEvent: LiveData<Event<Unit>>
        get() = _deletedEvent


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
                _toastMessage.value = Event(message)
                if (isSuccess) {
                    _modifiedEvent.value = Event(Unit)
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
                _toastMessage.value = Event(message)
                if (isSuccess) {
                    _deletedEvent.value = Event(Unit)
                }
            }, {
                Logger.d("$it")
            })
    }
}