package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.SocketException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    private val studyId: Int by lazy {
        savedState.get(KEY_STUDY_ID) ?: -1
    }

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private var disposable: Disposable? = null

    fun setup() {
        compositeDisposable += chatRepository.setChat(studyId)
            .networkSchedulers()
            .subscribe({
                getAllChats()
            }, {
                Logger.d("$it")
            })
    }

    private fun getAllChats() {
        disposable = chatRepository.getAll(studyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _chatList.value = groupByDate(ChatItem.listOf(it))
            }, {
                Logger.d("$it")
            })
    }

    private fun groupByDate(chatList: List<ChatItem>): List<ChatItem> {
        val list = mutableListOf<ChatItem>()
        val dateGroup = chatList.groupBy {
            SimpleDateFormat("yyyy년 MM월 dd일").format(it.timeStamp)
        }
        dateGroup.forEach {
            list.add(
                ChatItem(
                    uuid = "",
                    studyId = 0,
                    userId = 0,
                    name = "",
                    message = it.key,
                    timeStamp = 0,
                    isMyChat = false,
                    chatSendingState = ChatSendingState.SUCCESS
                )
            )
            list.addAll(it.value)
        }

        return list
    }

    fun onConnect() {
        chatRepository.onConnect(studyId)
    }

    override fun onCleared() {
        chatRepository.onDisconnect()
        super.onCleared()
    }

    fun onDisconnect() {
        chatRepository.onDisconnect()
    }

    fun sendMessage(message: String) {
        val chatMessage = message.trim()
        if (chatMessage.isNotEmpty()) {
            val uuid = UUID.randomUUID().toString()

            compositeDisposable += chatRepository.sendMessage(message, uuid)
                .networkSchedulers()
                .subscribe({

                }, {
                    if (it is SocketException) {
                        Timer().schedule(10000) {
                            _chatList.postValue(
                                chatList.value.orEmpty() + ChatItem(
                                    uuid = uuid,
                                    studyId = studyId,
                                    userId = AuthHolder.requireId(),
                                    name = UserHolder.get()?.nickname.orEmpty(),
                                    message = "$message - 전송 실패",
                                    timeStamp = System.currentTimeMillis(),
                                    isMyChat = true,
                                    chatSendingState = ChatSendingState.FAILURE
                                )
                            )
                        }
                    }

                })
        }
    }

    fun deleteBookmark() {
        compositeDisposable += chatRepository.deleteBookmark()
            .networkSchedulers()
            .subscribe({

            }, {
                Logger.d("$it")
            })
    }

    companion object {
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}