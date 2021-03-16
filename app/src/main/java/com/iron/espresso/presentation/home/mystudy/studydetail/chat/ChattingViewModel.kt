package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.entity.ChatUser
import com.iron.espresso.domain.usecase.GetChat
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign

class ChattingViewModel @ViewModelInject constructor(private val getChat: GetChat) : BaseViewModel() {

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

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
}