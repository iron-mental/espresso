package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

object InputItem

class InputChatAdapter(private val chatMessage: (String) -> Unit) :
    ListAdapter<InputItem, ChatInputViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatInputViewHolder {
        return ChatInputViewHolder(parent, chatMessage)
    }

    override fun onBindViewHolder(holder: ChatInputViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InputItem>() {
            override fun areItemsTheSame(oldItem: InputItem, newItem: InputItem): Boolean =
                true

            override fun areContentsTheSame(oldItem: InputItem, newItem: InputItem): Boolean =
                true
        }
    }
}


