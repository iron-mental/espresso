package com.iron.espresso.local.model

import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.source.remote.ChatRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    private val onChatSendReceiver = Emitter.Listener { args ->
        Logger.d("${args.map { it.toString() }}")
    }

    private val onChatReceiver = Emitter.Listener { args ->
        val response = JSONObject(args.getOrNull(0).toString())

        Logger.d("chatResponse: $response")
        if (response.getInt("user_id") == AuthHolder.requireId()) {
            Timer().schedule(500) {
                chatLocalDataSource.insert(
                    ChatEntity(
                        uuid = response.getString("uuid"),
                        studyId = response.getInt("study_id"),
                        userId = response.getInt("user_id"),
                        nickname = response.getString("nickname"),
                        message = response.getString("message"),
                        timeStamp = response.getLong("date")
                    )
                )
                    .networkSchedulers()
                    .subscribe()
            }
        } else {
            chatLocalDataSource.insert(
                ChatEntity(
                    uuid = response.getString("uuid"),
                    studyId = response.getInt("study_id"),
                    userId = response.getInt("user_id"),
                    nickname = response.getString("nickname"),
                    message = response.getString("message"),
                    timeStamp = response.getLong("date")
                )
            )
                .networkSchedulers()
                .subscribe()
        }
    }

    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.insert(chatEntity)

    override fun setChat(studyId: Int, first: Boolean) {
        var disposable: Disposable? = null
        disposable = chatLocalDataSource.getTimeStamp(studyId)
            .flatMap { timeStamp ->
                chatRemoteDataSource.getChat(studyId, timeStamp, first)
                    .map {
                        it.data?.toChatting()
                    }
            }
            .networkSchedulers()
            .subscribe({
                if (it != null) {
                    updateNicknames(it.chatUserList)
                    if (it.chatList.isNotEmpty()) {
                        chatLocalDataSource.insertAll(ChatEntity.of(it.chatList, it.chatUserList))
                            .networkSchedulers()
                            .subscribe()
                    }
                }
                disposable?.dispose()
            }, {
                disposable?.dispose()
            })
    }

    private fun updateNicknames(chatUserList: List<ChatUser>) {
        chatUserList.forEach {
            chatLocalDataSource.updateNickname(it.userId, it.nickname)
                .networkSchedulers()
                .subscribe()
        }
    }

    override fun onConnect(studyId: Int) {
        return chatRemoteDataSource.onConnect(studyId, onChatSendReceiver, onChatReceiver)
    }

    override fun onDisconnect() {
        return chatRemoteDataSource.onDisconnect(onChatSendReceiver, onChatReceiver)
    }

    override fun sendMessage(chatMessage: JsonObject) {
        return chatRemoteDataSource.sendMessage(chatMessage)
    }
}