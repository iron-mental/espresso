package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.graphics.Color
import androidx.annotation.ColorInt
import com.iron.espresso.AuthHolder
import com.iron.espresso.local.model.ChatEntity

data class ChatItem(
    val uuid: String,
    val studyId: Int,
    val userId: Int,
    val name: String,
    val message: String,
    val timeStamp: Long,
    val isMyChat: Boolean,
    val chatSendingState: ChatSendingState
) {
    companion object {

        fun of(chatEntityList: List<ChatEntity>): List<ChatItem> {
            return chatEntityList.map {
                ChatItem(
                    uuid = it.uuid,
                    studyId = it.studyId,
                    userId = it.userId,
                    name = it.nickname,
                    message = it.message,
                    timeStamp = it.timeStamp,
                    isMyChat = it.userId == AuthHolder.requireId(),
                    chatSendingState = ChatSendingState.SUCCESS
                )
            }
        }
    }
}

enum class ChatSendingState(@ColorInt val color: Int) {
    SENDING(Color.GRAY),
    SUCCESS(Color.WHITE),
    FAILURE(Color.RED)
}

