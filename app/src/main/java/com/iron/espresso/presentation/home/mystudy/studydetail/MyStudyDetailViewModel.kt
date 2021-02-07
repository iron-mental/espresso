package com.iron.espresso.presentation.home.mystudy.studydetail

import androidx.hilt.lifecycle.ViewModelInject
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import retrofit2.HttpException

class MyStudyDetailViewModel @ViewModelInject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    fun leaveStudy(studyId: Int) {
        compositeDisposable += studyRepository
            .leaveStudy(
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val errorResponse = (it as? HttpException)?.toErrorResponse()
                Logger.d("$errorResponse")
            })
    }
}