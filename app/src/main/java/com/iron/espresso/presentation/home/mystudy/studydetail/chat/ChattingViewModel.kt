package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ChattingViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    private val studyId: Int by lazy {
        savedState.get(KEY_STUDY_ID) ?: -1
    }

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

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
        compositeDisposable += chatRepository.getAll(studyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _chatList.value = ChatItem.of(it)
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })
    }

    fun onConnect() {
        chatRepository.onConnect(studyId)
    }

    fun onDisconnect() {
        chatRepository.onDisconnect()
    }

    fun sendMessage(message: String) {
        val chatMessage = message.trim()
        if (chatMessage.isNotEmpty()) {
            val uuid = UUID.randomUUID().toString()

            _chatList.value = chatList.value.orEmpty() + ChatItem(
                uuid = uuid,
                studyId = studyId,
                userId = AuthHolder.requireId(),
                name = UserHolder.get()?.nickname.orEmpty(),
                message = message,
                timeStamp = System.currentTimeMillis(),
                isMyChat = true,
                sent = false
            )

            val data = JsonObject()
            data.addProperty("message", chatMessage)
            data.addProperty("uuid", uuid)

            chatRepository.sendMessage(data)
        }
    }

    companion object {
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}