package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.gson.JsonObject
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.local.model.ChatEntity
import com.iron.espresso.model.source.remote.ChatRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.socket.emitter.Emitter
import org.json.JSONObject

class ChattingViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val chatRepository: ChatRepository,
    private val chatRemoteDataSource: ChatRemoteDataSource
) : BaseViewModel() {

    private val studyId: Int by lazy {
        savedState.get(KEY_STUDY_ID) ?: -1
    }

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    private val onChatReceiver = Emitter.Listener { args ->
        val response = JSONObject(args.getOrNull(0).toString())

        Logger.d("chatResponse: $response")
    }

    private val onChatSendReceiver = Emitter.Listener { args ->
        Logger.d("${args.map { it.toString() }}")
    }

    fun setChat(studyId: Int) {
        chatRepository.setChat(studyId, false)
    }

    fun getAllChats() {
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

    fun insert(chatEntity: ChatEntity) {
        compositeDisposable += chatRepository.insert(chatEntity)
            .networkSchedulers()
            .subscribe()
    }

    fun onConnect() {
        chatRemoteDataSource.onConnect(studyId, onChatSendReceiver, onChatReceiver)
    }

    fun onDisconnect() {
        chatRemoteDataSource.onDisconnect(onChatSendReceiver, onChatReceiver)
    }

    fun sendMessage(message: JsonObject) {
        chatRemoteDataSource.sendMessage(message)
    }

    companion object {
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}