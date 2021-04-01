package com.iron.espresso.local.model

import androidx.room.EmptyResultSetException
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
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    private val compositeDisposable = CompositeDisposable()
    private var chatUserList = listOf<ChatUser>()

    init {
        chatRemoteDataSource.setChatCallback { response ->
            val delay: Long = if (response.userId == AuthHolder.requireId()) {
                500
            } else {
                0
            }

            Timer().schedule(delay) {
                val jobList = mutableListOf<Completable>()
                jobList.add(chatLocalDataSource.insert(response.toChatEntity()))
                jobList.addAll(updateNicknames(chatUserList))
                compositeDisposable += Completable.concat(jobList)
                    .networkSchedulers()
                    .subscribe()
            }
        }
    }

    override fun getAll(studyId: Int): Flowable<List<ChatEntity>> =
        chatLocalDataSource.getAll(studyId)

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
                    chatUserList = chatting.chatUserList
                    val jobList = mutableListOf<Completable>()
                    if (timeStamp != -1L) {
                        jobList.addAll(updateNicknames(chatting.chatUserList))
                        if (chatting.chatList.isNotEmpty()) {
                            chatLocalDataSource.insert(
                                ChatEntity(
                                    uuid = "bookmark",
                                    studyId = studyId,
                                    userId = 0,
                                    nickname = "",
                                    message = "여기까지 읽었습니다",
                                    timeStamp = chatting.chatList[0].date - 1
                                )
                            )
                                .networkSchedulers()
                                .subscribe()
                        }
                    }
                    if (chatting.chatList.isNotEmpty()) {

                        jobList.add(
                            insertAll(
                                ChatEntity.of(
                                    chatting.chatList,
                                    chatting.chatUserList
                                )
                            )
                        )
                    }

                    if (jobList.isEmpty()) {
                        emitter.onComplete()
                    } else {
                        compositeDisposable += Completable.concat(jobList)
                            .networkSchedulers()
                            .subscribe({
                                emitter.onComplete()
                            }, { throwable ->
                                emitter.onError(throwable)
                                Logger.d("$throwable")
                            })
                    }
                }

            }, {
                emitter.onError(it)
                Logger.d("$it")
            })

    private fun updateNicknames(chatUserList: List<ChatUser>): List<Completable> {
        return chatUserList.map {
            chatLocalDataSource.updateNickname(it.userId, it.nickname)
        }
    }

    private fun insertAll(chatList: List<ChatEntity>): Completable {
        return chatLocalDataSource.insertAll(chatList)
    }

    override fun deleteBookmark(): Completable {
        return chatLocalDataSource.deleteBookmark()
    }

    override fun delete(studyId: Int): Completable {
        return chatLocalDataSource.delete(studyId)
    }

    override fun deleteAll(studyId: Int): Completable {
        return chatLocalDataSource.deleteAll(studyId)
    }

    override fun onConnect(studyId: Int) {
        return chatRemoteDataSource.onConnect(studyId)
    }

    override fun onDisconnect() {
        compositeDisposable.clear()
        return chatRemoteDataSource.onDisconnect()
    }

    override fun sendMessage(chatMessage: String, uuid: String): Completable {
        return chatRemoteDataSource.sendMessage(chatMessage, uuid)
    }
}