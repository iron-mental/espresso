package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import com.iron.espresso.databinding.ItemChatBinding
import com.iron.espresso.databinding.ItemChatInputBinding
import java.text.SimpleDateFormat

class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            chatAdapter.submitList(
                chatAdapter.currentList +
                    ChatItem(
                        "원우석",
                        chatMessage,
                        "[오후: 10 : 19]",
                        System.currentTimeMillis(),
                        true
                    )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter


        chatAdapter.submitList(
            listOf(
                ChatItem(
                    "최용권",
                    "[마이데일리 = 허설희 기자] 배우 남궁민이 데뷔 19년 만에 첫 대상을 거머쥐었다. '2020년 SBS 연기대상'이 31일 오후 상암 SBS프리즘타워에서 신동엽, 김유정 사회로 진행됐다. 이날 대상의 주인공은 '스토브리그'의 남궁민이었다.",
                    "[오후: 10 : 00]",
                    System.currentTimeMillis(),
                    false
                ),
                ChatItem(
                    "최용권",
                    "[마이데일리 = 허설희 기자] 배우 남궁민이 데뷔 19년 만에 첫 대상을 거머쥐었다. '2020년 SBS 연기대상'이 31일 오후 상암 SBS프리즘타워에서 신동엽, 김유정 사회로 진행됐다. 이날 대상의 주인공은 '스토브리그'의 남궁민이었다.",
                    "[오후: 10 : 01]",
                    System.currentTimeMillis(),
                    false
                ),
                ChatItem(
                    "원우석",
                    "[마이데일리 = 허설희 기자] 배우 남궁민이 데뷔 19년 만에 첫 대상을 거머쥐었다. '2020년 SBS 연기대상'이 31일 오후 상암 SBS프리즘타워에서 신동엽, 김유정 사회로 진행됐다. 이날 대상의 주인공은 '스토브리그'의 남궁민이었다.",
                    "[오후: 10 : 10]",
                    System.currentTimeMillis(),
                    true
                ),
                ChatItem(
                    "원우석",
                    "[마이데일리 = 허설희 기자] 배우 남궁민이 데뷔 19년 만에 첫 대상을 거머쥐었다. '2020년 SBS 연기대상'이 31일 오후 상암 SBS프리즘타워에서 신동엽, 김유정 사회로 진행됐다. 이날 대상의 주인공은 '스토브리그'의 남궁민이었다.",
                    "[오후: 10 : 19]",
                    System.currentTimeMillis(),
                    true
                )
            )
        )

        inputChatAdapter.submitList(
            listOf(
                ChatItem(
                    "원우석",
                    "[마이데일리 = 허설희 기자] 배우 남궁민이 데뷔 19년 만에 첫 대상을 거머쥐었다. '2020년 SBS 연기대상'이 31일 오후 상암 SBS프리즘타워에서 신동엽, 김유정 사회로 진행됐다. 이날 대상의 주인공은 '스토브리그'의 남궁민이었다.",
                    "[오후: 10 : 19]",
                    System.currentTimeMillis(),
                    true
                )
            )
        )

    }

    companion object {
        fun newInstance() =
            ChattingFragment()
    }
}

class ChatAdapter : ListAdapter<ChatItem, ChatViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
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

data class ChatItem(
    val name: String,
    val message: String,
    val time: String,
    val timeStamp: Long,
    val isMyChat: Boolean,
)

class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemChatBinding = DataBindingUtil.bind(itemView)!!

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

class ChatInputViewHolder(view: View, private val chatMessage: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val binding: ItemChatInputBinding = DataBindingUtil.bind(itemView)!!

    private val dateFormat = SimpleDateFormat("[a hh:mm]")

    fun bind(item: ChatItem) {
        with(binding) {
            name.text = item.name + " $"
            refreshTime()

            inputChat.setOnFocusChangeListener { _, _ ->
                refreshTime()
            }

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



