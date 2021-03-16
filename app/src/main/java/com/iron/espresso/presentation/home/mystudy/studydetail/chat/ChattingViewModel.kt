package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.model.response.study.ChatUser

class ChattingViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    fun getChat(studyId: Int) {
        compositeDisposable += ApiModule.provideStudyApi().getChat(
            studyId = studyId,
            date = 1613638463257,
            first = true
        ).map {
            it.data
        }
            .networkSchedulers()
            .subscribe({
                if (it != null) {
                    setNickname(it.userList.orEmpty())

                    _chatList.value = it.toChatItem()
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