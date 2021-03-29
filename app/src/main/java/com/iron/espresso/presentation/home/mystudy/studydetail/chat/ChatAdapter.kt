package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter : ListAdapter<ChatItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> {
                ChatSystemViewHolder(parent)
            }
            else -> {
                ChatViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            0 -> {
                (holder as ChatSystemViewHolder).bind(currentList[position])
            }
            else -> {
                (holder as ChatViewHolder).bind(currentList[position])
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains(CHECK_ACTIVATE)) {
            (holder as ChatViewHolder).checkActivation(currentList[position].sent)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].userId
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