package com.iron.espresso.presentation.home.study.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import retrofit2.HttpException

class StudyListViewModel @ViewModelInject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _studyList = MutableLiveData<List<StudyItem>>()
    val studyList: LiveData<List<StudyItem>>
        get() = _studyList

    fun getStudyList(category: String, sort: String) {
        compositeDisposable += studyApi
            .getStudyList(
                bearerToken = AuthHolder.bearerToken,
                category = category,
                sort = sort
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
                _studyList.value = it.data?.map { studyResponse ->
                    studyResponse.toStudyItem()
                }
            }, {
                Logger.d("$it")
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("${errorResponse.message}")
                }
            })
    }

}