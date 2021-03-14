package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemChatBinding
import java.text.SimpleDateFormat

data class ChatItem(
    val name: String,
    val message: String,
    val timeStamp: Long,
    val isMyChat: Boolean,
)

