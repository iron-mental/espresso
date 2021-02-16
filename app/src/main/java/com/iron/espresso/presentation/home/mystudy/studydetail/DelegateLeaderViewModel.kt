package com.iron.espresso.presentation.home.mystudy.studydetail

import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.api.DelegateRequest

class DelegateLeaderViewModel : BaseViewModel() {

    fun delegateStudyLeader(studyId: Int, newLeader: Int) {
        compositeDisposable += ApiModule.provideStudyApi()
            .delegateStudyLeader(
                studyId = studyId,
                body = DelegateRequest(
                    newLeader = newLeader
                )
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }
}