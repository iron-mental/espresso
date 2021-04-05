package com.iron.espresso.presentation.home.apply

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.usecase.GetApplyList
import com.iron.espresso.domain.usecase.GetMyApplyList
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplyStudyListViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val getApplyList: GetApplyList,
    private val getMyApplyList: GetMyApplyList
) :
    BaseViewModel() {

    val type: ApplyListFragment.Type by lazy {
        state.get<ApplyListFragment.Type>(KEY_TYPE) ?: ApplyListFragment.Type.NONE
    }

    val studyId: Int by lazy {
        (state.get(KEY_STUDY_ID) as? Int) ?: -1
    }

    val emptyViewMessage: Int
        get() = if (type == ApplyListFragment.Type.MY) R.string.empty_my_apply else R.string.empty_apply

    private val _applyList = MutableLiveData<List<ApplyStudyItem>>()
    val applyList: LiveData<List<ApplyStudyItem>>
        get() = _applyList

    init {
        getList()
    }

    fun getList() {
        compositeDisposable += (if (type == ApplyListFragment.Type.NONE) getApplyList(studyId) else getMyApplyList())
            .map {
                it.map { apply ->
                    ApplyStudyItem.of(apply)
                }
            }
            .networkSchedulers()
            .subscribe({ list ->
                _applyList.value = list
            }, {
                Logger.d("$it")
            })
    }

    companion object {
        const val KEY_TYPE = "TYPE"
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}