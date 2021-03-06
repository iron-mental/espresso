package com.iron.espresso.presentation.home.mystudy.studydetail

import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.presentation.home.study.AbsStudyDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class StudyInfoViewModel @Inject constructor(private val studyApi: StudyApi) :
    AbsStudyDetailViewModel() {

    fun getStudy(studyId: Int) {
        compositeDisposable += studyApi
            .getStudyDetail(
                bearerToken = AuthHolder.bearerToken,
                studyId = studyId
            )
            .networkSchedulers()
            .subscribe({
                if (it.data != null) {
                    _studyDetail.value = it.data.toStudyDetailItem()
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