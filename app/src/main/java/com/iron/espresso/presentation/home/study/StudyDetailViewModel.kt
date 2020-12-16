package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.response.study.StudyDetailResponse
import retrofit2.HttpException

class StudyDetailViewModel : BaseViewModel() {

    private val _studyDetail = MutableLiveData<StudyDetailResponse>()
    val studyDetail: LiveData<StudyDetailResponse>
        get() = _studyDetail

    @SuppressLint("CheckResult")
    fun getStudy(studyId: Int) {
        ApiModule.provideStudyApi()
            .getStudyDetail(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyDetail.value = it.data
                }
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                if (errorResponse != null) {
                    Logger.d("$errorResponse")
                }
            })
    }
}