package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URI

class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chatAdapter by lazy { ChatAdapter() }
    private val inputChatAdapter by lazy {
        InputChatAdapter { chatMessage ->
            chatAdapter.submitList(
                chatAdapter.currentList +
                    ChatItem(
                        "원우석",
                        chatMessage,
                        System.currentTimeMillis(),
                        true
                    )
            )

            val message = chatMessage.trim()
            if (message.isNotEmpty()) {
                chatSocket?.emit(CHAT, message)
            }
        }
    }

    private var chatSocket: Socket? = null

    private val onChatSendReceiver = Emitter.Listener { args ->
        activity?.runOnUiThread {
            Logger.d("${args.map { it.toString() }}")
        }
    }

    private val onChatReceiver = Emitter.Listener { args ->
        activity?.runOnUiThread {
            val response = args.getOrNull(0)

            Logger.d("chatResponse: $response")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter

        inputChatAdapter.submitList(
            listOf(
                ChatItem(
                    "원우석",
                    "",
                    System.currentTimeMillis(),
                    true
                )
            )
        )

        val studyId = arguments?.getInt(MyStudyDetailActivity.STUDY_ID) ?: -1
        connect(studyId)
    }

    override fun onDestroyView() {
        chatSocket?.disconnect()
        chatSocket?.off(CHAT, onChatSendReceiver)
        chatSocket?.off(CHAT_MESSAGE, onChatReceiver)
        super.onDestroyView()
    }

    private fun connect(studyId: Int) {
        if (chatSocket?.connected() == true) return

        val options = IO.Options().apply {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            callFactory = client
            webSocketFactory = client
            transports = arrayOf("websocket")
            query = "token=${AuthHolder.accessToken}&study_id=$studyId"
        }

        val chatManager = Manager(URI("https://www.terminal-study.tk"), options)

        chatSocket =
            chatManager.socket("/terminal").apply {
                on(CHAT, onChatSendReceiver)
                on(CHAT_MESSAGE, onChatReceiver)

                on(Socket.EVENT_CONNECT) {
                    Logger.d("EVENT_CONNECT ${it.map { it.toString() }}")
                }

                on(Socket.EVENT_DISCONNECT) {
                    Logger.d("EVENT_DISCONNECT ${it.map { it.toString() }}")
                }

                on(Socket.EVENT_MESSAGE) {
                    Logger.d("EVENT_MESSAGE ${it.map { it.toString() }}")
                }

                connect()
            }
    }

    companion object {
        private const val CHAT = "chat"
        private const val CHAT_MESSAGE = "message"

        fun newInstance(studyId: Int) =
            ChattingFragment().apply {
                arguments = Bundle().apply {
                    putInt(MyStudyDetailActivity.STUDY_ID, studyId)
                }
            }
    }
}