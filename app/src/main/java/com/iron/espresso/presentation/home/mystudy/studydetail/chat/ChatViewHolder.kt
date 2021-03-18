package com.iron.espresso.presentation.home.mystudy.studydetail.chat

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
        }
    }
}