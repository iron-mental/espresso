package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.domain.usecase.GetChat
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.local.model.ChatEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ChattingViewModel @ViewModelInject constructor(
    @Assisted
    private val savedState: SavedStateHandle,
    private val getChat: GetChat,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    private val studyId: Int by lazy {
        savedState.get(KEY_STUDY_ID) ?: -1
    }

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    private val _timeStamp = MutableLiveData<Long>()
    val timeStamp: LiveData<Long> get() = _timeStamp

    fun getTimeStamp() {
        chatRepository.getTimeStamp(studyId)
    }

    fun getChat(studyId: Int) {
        compositeDisposable += getChat(
            studyId = studyId,
            date = -1,
            first = false
        )
            .networkSchedulers()
            .subscribe({
                if (it != null) {
                    setNickname(it.chatUserList)
                    insertAll(ChatItem.of(it.chatList, it.chatUserList))
                }
                Logger.d("$it")
            }, {
                Logger.d("$it")
            })

    }

    private fun setNickname(userList: List<ChatUser>) {
        val user = userList.find {
            it.userId == AuthHolder.requireId()
        }
        this._userNickname.value = user?.nickname
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

    fun insert(chatEntity: ChatEntity) = viewModelScope.launch {
        chatRepository.insert(chatEntity)
    }

    private fun insertAll(chat: List<ChatItem>) = viewModelScope.launch {
        chatRepository.insertAll(chat.map {
            ChatEntity(
                uuid = it.uuid,
                studyId = it.studyId,
                userId = it.userId,
                nickname = it.name,
                message = it.message,
                timeStamp = it.timeStamp,
                isMyChat = it.isMyChat
            )
        })
    }

    companion object {
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}