package com.iron.espresso.presentation.home.study.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.presentation.home.study.HotKeywordItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotKeywordViewModel @Inject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _hotKeywordList = MutableLiveData<List<HotKeywordItem>>()
    val hotKeywordList: LiveData<List<HotKeywordItem>>
        get() = _hotKeywordList

    fun getHotKeywordList() {
        compositeDisposable += studyRepository
            .getHotSearchKeyword()
            .map {
                it.map { hotSearchKeywordResponse ->
                    hotSearchKeywordResponse.toHotKeywordItem()
                }
            }
            .networkSchedulers()
            .subscribe({
                _hotKeywordList.value = it
                Logger.d("it")
            }, {
                Logger.d("it")
            })
    }
}