package com.iron.espresso.local.model

import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.source.remote.ChatRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {
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
}