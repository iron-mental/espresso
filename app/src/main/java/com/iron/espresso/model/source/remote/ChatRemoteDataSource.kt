package com.iron.espresso.model.source.remote

import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.ChattingResponse
import io.reactivex.Single
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(private val studyApi: StudyApi) :
    ChatRemoteDataSource {
    override fun getChat(studyId: Int, date: Long, first: Boolean): Single<BaseResponse<ChattingResponse>> {
        return studyApi.getChat(studyId = studyId, date = date, first = first)
    }
}

interface ChatRemoteDataSource {
    fun getChat(studyId: Int, date: Long, first: Boolean): Single<BaseResponse<ChattingResponse>>
}
