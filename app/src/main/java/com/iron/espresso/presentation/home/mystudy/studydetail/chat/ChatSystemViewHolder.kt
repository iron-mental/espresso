package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemChatSystemBinding

class ChatSystemViewHolder(
    parent: ViewGroup,
    private val binding: ItemChatSystemBinding =
        ItemChatSystemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ChatItem) {
        with(binding) {
            message.text = item.message
        }
    }
}