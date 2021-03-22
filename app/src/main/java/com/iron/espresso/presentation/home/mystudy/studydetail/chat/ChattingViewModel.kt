package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.iron.espresso.Logger
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.domain.repo.ChatRepository
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.local.model.ChatEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

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

    companion object {
        const val KEY_STUDY_ID = "STUDY_ID"
    }
}