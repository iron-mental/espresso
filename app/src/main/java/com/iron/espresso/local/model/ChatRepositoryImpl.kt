package com.iron.espresso.local.model

import com.iron.espresso.domain.entity.Chatting
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.model.source.remote.ChatRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {
    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

    override fun getTimeStamp(studyId: Int): Single<Long> =
        chatLocalDataSource.getTimeStamp(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.insert(chatEntity)

    override fun insertAll(chatEntity: List<ChatEntity>): Completable =
        chatLocalDataSource.insertAll(chatEntity)

    override fun delete(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.delete(chatEntity)

    override fun getChat(studyId: Int, date: Long, first: Boolean): Single<Chatting> =
        chatRemoteDataSource.getChat(studyId, date, first).map {
            it.data?.toChatting()
        }
}