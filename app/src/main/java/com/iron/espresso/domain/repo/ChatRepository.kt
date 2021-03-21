package com.iron.espresso.domain.repo

import com.iron.espresso.local.model.ChatEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ChatRepository {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun getTimeStamp(studyId: Int): Single<Long>

    fun insert(chatEntity: ChatEntity): Completable

    fun insertAll(chatEntity: List<ChatEntity>): Completable

    fun delete(chatEntity: ChatEntity): Completable
}