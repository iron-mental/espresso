package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers

class StudyDetailViewModel : BaseViewModel() {

    @SuppressLint("CheckResult")
    fun getStudy(studyId: Int) {
        ApiModule.provideStudyApi()
            .getStudyDetail(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }
}