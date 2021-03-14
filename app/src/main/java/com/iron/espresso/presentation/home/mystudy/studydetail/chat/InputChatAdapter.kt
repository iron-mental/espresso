package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.iron.espresso.R
import com.iron.espresso.presentation.home.mystudy.studydetail.ChatItem

class InputChatAdapter(private val chatMessage: (String) -> Unit) :
    ListAdapter<ChatItem, ChatInputViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatInputViewHolder {
        return ChatInputViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_input, parent, false),
            chatMessage
        )
    }

    override fun onBindViewHolder(holder: ChatInputViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean =
                oldItem.message == newItem.message

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean =
                oldItem == newItem
        }
    }
}


