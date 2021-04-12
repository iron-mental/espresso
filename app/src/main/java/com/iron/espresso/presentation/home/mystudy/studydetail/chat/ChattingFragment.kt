package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import com.iron.espresso.ext.dp
import com.iron.espresso.ext.hideLoading
import com.iron.espresso.ext.showLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chattingViewModel by viewModels<ChattingViewModel>()

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            chatAdapter.submitList(
                chatAdapter.currentList + ChatItem(
                    uuid = "",
                    studyId = 0,
                    userId = AuthHolder.requireId(),
                    name = UserHolder.get()?.nickname.orEmpty(),
                    message = chatMessage,
                    timeStamp = System.currentTimeMillis(),
                    isMyChat = true,
                    chatSendingState = ChatSendingState.SENDING
                )
            )
            chattingViewModel.sendMessage(chatMessage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)
            var scrollDy = 0
            chatList.adapter = adapter

            chatList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    scrollDy -= dy
                    binding.moveDownButton.isVisible = scrollDy > 800.dp
                }
            })
            moveDownButton.setOnClickListener {
                (chatList.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(chatAdapter.currentList.lastIndex, 0)

                scrollDy = 0
                chatList.stopScroll()
            }
        }

        chattingViewModel.run {
            chatList.observe(viewLifecycleOwner, { chatList ->
                chatAdapter.submitList(chatList)
                if (inputChatAdapter.currentList.isEmpty()) {
                    inputChatAdapter.submitList(listOf(InputChatItem))
                    setScrollPosition()
                }
                hideLoading()
            })
        }
    }

    private fun setScrollPosition() {
        val findItem = chatAdapter.currentList.find { it.uuid == "bookmark" }
        if (findItem != null) {
            (binding.chatList.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(chatAdapter.currentList.lastIndexOf(findItem), 0)
        }
        (binding.chatList.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(chatAdapter.currentList.lastIndex, 0)
    }

    override fun onStart() {
        super.onStart()
        showLoading()
        chattingViewModel.onConnect()
        chattingViewModel.setup()
    }

    override fun onStop() {
        chattingViewModel.deleteBookmark()
        chattingViewModel.onDisconnect()
        super.onStop()
    }

    override fun onDestroyView() {
        chattingViewModel.deleteBookmark()
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