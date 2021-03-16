package com.iron.espresso.domain.entity

data class Chatting(
    val chatList: List<Chat>,
    val chatUserList: List<ChatUser>
)

data class Chat(
    val uuid: String,
    val studyId: Int,
    val userId: Int,
    val message: String,
    val date: Long
)

data class ChatUser(
    val userId: Int,
    val nickname: String
)