package com.iron.espresso.presentation.home.mystudy.studydetail

import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign

class MyStudyDetailViewModel : BaseViewModel() {

    fun leaveStudy(studyId: Int) {
        compositeDisposable += ApiModule.provideStudyApi()
            .leaveStudy(
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