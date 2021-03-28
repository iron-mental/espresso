package com.iron.espresso.local.model

import androidx.room.EmptyResultSetException
import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.entity.Chatting
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.source.remote.ChatRemoteDataSource
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    private val compositeDisposable = CompositeDisposable()

    init {
        chatRemoteDataSource.setChatCallback { response ->
            if (response.userId == AuthHolder.requireId()) {
                Timer().schedule(500) {
                    chatLocalDataSource.insert(
                        ChatEntity(
                            uuid = response.uuid.orEmpty(),
                            studyId = response.studyId,
                            userId = response.userId,
                            nickname = response.nickname.orEmpty(),
                            message = response.message.orEmpty(),
                            timeStamp = response.date
                        )
                    )
                        .networkSchedulers()
                        .subscribe()
                }
            } else {
                chatLocalDataSource.insert(
                    ChatEntity(
                        uuid = response.uuid.orEmpty(),
                        studyId = response.studyId,
                        userId = response.userId,
                        nickname = response.nickname.orEmpty(),
                        message = response.message.orEmpty(),
                        timeStamp = response.date
                    )
                )
                    .networkSchedulers()
                    .subscribe()
            }
        }
    }

    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

    override fun insert(chatEntity: ChatEntity): Completable =
        chatLocalDataSource.insert(chatEntity)

    override fun setChat(studyId: Int): Completable {
        return Completable.create { emitter ->
            compositeDisposable += chatLocalDataSource.getTimeStamp(studyId)
                .networkSchedulers()
                .subscribe({ timeStamp ->
                    compositeDisposable += requestChat(studyId, timeStamp, emitter)
                }, {
                    if (it is EmptyResultSetException) {
                        compositeDisposable += requestChat(studyId, -1, emitter)
                    } else {
                        emitter.onError(it)
                    }
                })
        }
    }

    private fun requestChat(studyId: Int, timeStamp: Long, emitter: CompletableEmitter) =
        chatRemoteDataSource.getChat(studyId, timeStamp, timeStamp == -1L)
            .map {
                it.data?.toChatting()
            }
            .networkSchedulers()
            .subscribe({ chatting: Chatting? ->
                if (chatting != null) {
                    if (timeStamp != -1L) {
                        updateNicknames(chatting.chatUserList)
                    }
                    if (chatting.chatList.isNotEmpty()) {
                        insertAll(ChatEntity.of(chatting.chatList, chatting.chatUserList))
                    }
                }
                emitter.onComplete()
            }, {
                emitter.onError(it)
                Logger.d("$it")
            })


    private fun updateNicknames(chatUserList: List<ChatUser>) {
        chatUserList.forEach {
            var disposable: Disposable? = null
            disposable = chatLocalDataSource.updateNickname(it.userId, it.nickname)
                .networkSchedulers()
                .subscribe({
                    disposable?.dispose()
                }, { throwable ->
                    Logger.d("$throwable")
                    disposable?.dispose()
                })
        }
    }

    private fun insertAll(chatList: List<ChatEntity>) {
        var disposable: Disposable? = null
        disposable = chatLocalDataSource.insertAll(chatList)
            .networkSchedulers()
            .subscribe({
                disposable?.dispose()
            }, {
                Logger.d("$it")
                disposable?.dispose()
            })
    }

    override fun onConnect(studyId: Int) {
        return chatRemoteDataSource.onConnect(studyId)
    }

    override fun onDisconnect() {
        compositeDisposable.clear()
        return chatRemoteDataSource.onDisconnect()
    }

    override fun sendMessage(chatMessage: JsonObject) {
        return chatRemoteDataSource.sendMessage(chatMessage)
    }
}