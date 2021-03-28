package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chattingViewModel by viewModels<ChattingViewModel>()

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            chattingViewModel.sendMessage(chatMessage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter

        inputChatAdapter.submitList(listOf(InputItem))

        chattingViewModel.run {
            chatList.observe(viewLifecycleOwner, {
                chatAdapter.submitList(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        chattingViewModel.onConnect()
        chattingViewModel.setup()
    }

    override fun onStop() {
        chattingViewModel.onDisconnect()
        super.onStop()
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