package com.iron.espresso.domain.repo

import com.google.gson.JsonObject
import com.iron.espresso.local.model.ChatEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ChatRepository {
    fun getAll(studyId: Int): Flowable<List<ChatEntity>>

    fun insert(chatEntity: ChatEntity): Completable

    fun setChat(studyId: Int): Completable

    fun onConnect(studyId: Int)

    fun onDisconnect()

    fun sendMessage(chatMessage: JsonObject)
}