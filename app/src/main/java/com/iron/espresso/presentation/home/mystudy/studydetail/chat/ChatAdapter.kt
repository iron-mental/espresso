package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ChatAdapter : ListAdapter<ChatItem, ChatViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains(CHECK_ACTIVATE)) {
            holder.checkActivation(currentList[position].sent)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        private const val CHECK_ACTIVATE = "check_activate"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatItem>() {

            override fun getChangePayload(oldItem: ChatItem, newItem: ChatItem): Any? {
                if (oldItem.sent != newItem.sent) return CHECK_ACTIVATE

                return super.getChangePayload(oldItem, newItem)
            }

            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean =
                oldItem.uuid == newItem.uuid

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean =
                oldItem == newItem
        }
    }
}