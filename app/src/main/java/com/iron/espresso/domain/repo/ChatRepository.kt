package com.iron.espresso.domain.repo

import com.iron.espresso.domain.entity.LocalChat
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface ChatRepository {
    fun getAll(studyId: Int): Flowable<List<LocalChat>>

    fun setChat(studyId: Int): Completable

    fun deleteBookmark(): Completable

    fun delete(studyId: Int): Completable

    fun deleteAll(studyId: Int): Completable

    fun onConnect(studyId: Int)

    fun onDisconnect()

    fun sendMessage(chatMessage: String, uuid: String): Completable
}