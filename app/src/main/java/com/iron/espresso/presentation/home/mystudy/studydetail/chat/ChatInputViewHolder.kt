package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.UserHolder
import com.iron.espresso.databinding.ItemChatInputBinding
import com.iron.espresso.ext.multilineIme
import java.text.SimpleDateFormat

class ChatInputViewHolder(
    parent: ViewGroup,
    private val chatMessage: (String) -> Unit,
    private val binding: ItemChatInputBinding =
        ItemChatInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) :
    RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = SimpleDateFormat("[a hh:mm]")

    fun bind() {
        with(binding) {
            name.text = UserHolder.get()?.nickname + " $"
            refreshTime()

            inputChat.setOnFocusChangeListener { _, _ ->
                refreshTime()
            }

            inputChat.multilineIme(EditorInfo.IME_ACTION_SEND)

            inputChat.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    val message = inputChat.text.toString()
                    if (message.isNotEmpty()) {
                        chatMessage(message)
                        inputChat.text.clear()
                        refreshTime()
                        return@setOnEditorActionListener false
                    }
                }
                true
            }
        }
    }

    private fun refreshTime() {
        binding.time.text = dateFormat.format(System.currentTimeMillis())
    }
}
