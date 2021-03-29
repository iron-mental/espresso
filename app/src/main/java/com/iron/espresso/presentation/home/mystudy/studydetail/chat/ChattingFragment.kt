package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chattingViewModel by viewModels<ChattingViewModel>()
    private val studyId: Int by lazy {
        arguments?.getInt(ChattingViewModel.KEY_STUDY_ID) ?: -1
    }

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            val uuid = UUID.randomUUID().toString()
            chatAdapter.submitList(
                chatAdapter.currentList +
                    ChatItem(
                        uuid = uuid,
                        studyId = studyId,
                        userId = AuthHolder.requireId(),
                        name = UserHolder.get()?.nickname.orEmpty(),
                        message = chatMessage,
                        timeStamp = System.currentTimeMillis(),
                        isMyChat = true,
                        sent = false
                    )
            )

            val data = JsonObject()
            data.addProperty("message", chatMessage.trim())
            data.addProperty("uuid", uuid)

            chattingViewModel.sendMessage(data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter

        chattingViewModel.run {
            onConnect()
            setChat(studyId)
            getAllChats()
            chatList.observe(viewLifecycleOwner, {
                chatAdapter.submitList(it)

                if (inputChatAdapter.currentList.isEmpty()) {
                    inputChatAdapter.submitList(listOf(InputChatItem))
                    (binding.chatList.layoutManager as LinearLayoutManager)
                }
            })
        }
    }

    override fun onDestroyView() {
        chattingViewModel.onDisconnect()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(studyId: Int) =
            ChattingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ChattingViewModel.KEY_STUDY_ID, studyId)
                }
            }
    }
}