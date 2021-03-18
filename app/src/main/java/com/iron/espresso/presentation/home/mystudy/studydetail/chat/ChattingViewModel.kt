package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.usecase.GetChat
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.local.model.Chat
import com.iron.espresso.local.model.ChatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ChattingViewModel @ViewModelInject constructor(
    private val getChat: GetChat,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    private val _allChats = MutableLiveData<List<Chat>>()
    val allChats: LiveData<List<Chat>> get() = _allChats

    fun getChat(studyId: Int) {
        compositeDisposable += getChat(
            studyId = studyId,
            date = 1613638463257,
            first = true
        )
            .networkSchedulers()
            .subscribe({
                if (it != null) {
                    setNickname(it.chatUserList)

                    _chatList.value = ChatItem.of(it.chatList, it.chatUserList)
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
        compositeDisposable += chatRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _allChats.value = it
                Logger.d("$it")
            },{
                Logger.d("$it")
            })
    }

    fun insert(chat: Chat) = viewModelScope.launch {
        chatRepository.insert(chat)
    }

    fun insertAll(chat: List<Chat>) = viewModelScope.launch {
        chatRepository.insertAll(chat)
    }
}