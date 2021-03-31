package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemChatBinding
import java.text.SimpleDateFormat

class ChatViewHolder(
    parent: ViewGroup,
    private val binding: ItemChatBinding =
        ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = SimpleDateFormat("[a hh:mm]")

    fun bind(item: ChatItem) {
        with(binding) {
            name.text = item.name + " $"
            message.text = item.message
            time.text = dateFormat.format(item.timeStamp)
            myChat.isVisible = item.isMyChat

            checkActivation(item.chatSendingState)
        }
    }

    fun checkActivation(state: ChatSendingState) {
        with(binding) {
            when (state) {
                ChatSendingState.SENDING -> {
                    name.setTextColor(Color.GRAY)
                    message.setTextColor(Color.GRAY)
                    time.setTextColor(Color.GRAY)
                    myChat.setTextColor(Color.GRAY)
                }
                ChatSendingState.SUCCESS -> {
                    name.setTextColor(Color.WHITE)
                    message.setTextColor(Color.WHITE)
                    time.setTextColor(Color.WHITE)
                    myChat.setTextColor(Color.WHITE)
                }

                ChatSendingState.FAILURE -> {
                    name.setTextColor(Color.RED)
                    message.setTextColor(Color.RED)
                    time.setTextColor(Color.RED)
                    myChat.setTextColor(Color.RED)
                }
            }
        }
    }
}