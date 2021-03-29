package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import com.iron.espresso.ext.hideLoading
import com.iron.espresso.ext.showLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chattingViewModel by viewModels<ChattingViewModel>()

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            val message = chatMessage.trim()
            if (message.isNotEmpty()) {
                chattingViewModel.sendMessage(message)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter

        chattingViewModel.run {
            chatList.observe(viewLifecycleOwner, {
                chatAdapter.submitList(it)

                if (inputChatAdapter.currentList.isEmpty()) {
                    inputChatAdapter.submitList(listOf(InputChatItem))
                    (binding.chatList.layoutManager as LinearLayoutManager)
                        .scrollToPositionWithOffset(chatAdapter.currentList.lastIndex, 0)
                }
                hideLoading()
            })
        }
    }

    override fun onStart() {
        super.onStart()
        showLoading()
        chattingViewModel.onConnect()
        chattingViewModel.setup()
    }

    override fun onStop() {
        chattingViewModel.onDisconnect()
        super.onStop()
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