package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemChatBinding
import com.iron.espresso.ext.setTextColorIf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class ChatViewHolder(
    parent: ViewGroup,
    private val binding: ItemChatBinding =
        ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = SimpleDateFormat("[a hh:mm]")
    private val timer = Timer()
    private var timerTask = timerTask { sendFailed() }

    fun bind(item: ChatItem) {
        with(binding) {
            name.text = item.name + " $"
            message.text = item.message
            time.text = dateFormat.format(item.timeStamp)
            myChat.isVisible = item.isMyChat

            checkActivation(item.sent)
        }
    }

    fun checkActivation(sent: Boolean) {
        with(binding) {
            name.setTextColorIf(Color.WHITE, Color.GRAY, sent)
            message.setTextColorIf(Color.WHITE, Color.GRAY, sent)
            time.setTextColorIf(Color.WHITE, Color.GRAY, sent)
            myChat.setTextColorIf(Color.WHITE, Color.GRAY, sent)

            if (sent) {
                timerTask.cancel()
            } else {
                timerTask = timerTask { sendFailed() }
                timer.schedule(timerTask, 2000)
            }
        }
    }

    private fun sendFailed() {
        with(binding) {
            name.setTextColor(Color.RED)
            message.setTextColor(Color.RED)
            time.setTextColor(Color.RED)
            myChat.setTextColor(Color.RED)
        }
    }
}