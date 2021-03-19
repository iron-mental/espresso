package com.iron.espresso.presentation.home.mystudy.studydetail.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentChattingBinding
import com.iron.espresso.local.model.Chat
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.net.URI
import java.util.*

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val chattingViewModel by viewModels<ChattingViewModel>()

    private var nickname: String? = null
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
                        name = nickname.orEmpty(),
                        message = chatMessage,
                        timeStamp = System.currentTimeMillis(),
                        isMyChat = true
                    )
            )

            val data = JsonObject()
            data.addProperty("message", chatMessage.trim())
            data.addProperty("uuid", uuid)

            chatSocket?.emit(CHAT, data)
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
            val response = JSONObject(args.getOrNull(0).toString())

            Logger.d("chatResponse: $response")
            chattingViewModel.insert(
                Chat(
                    uuid = response.getString("uuid"),
                    studyId = response.getInt("study_id"),
                    userId = AuthHolder.requireId(),
                    nickname = response.getString("nickname"),
                    message = response.getString("message"),
                    timeStamp = response.getLong("date"),
                    isMyChat = response.getInt("user_id") == AuthHolder.requireId()
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConcatAdapter(chatAdapter, inputChatAdapter)

        binding.chatList.adapter = adapter

        inputChatAdapter.submitList(
            listOf(
                ChatItem(
                    "",
                    0,
                    0,
                    "원우석",
                    "",
                    System.currentTimeMillis(),
                    true
                )
            )
        )

        connect(studyId)

        chattingViewModel.run {
            getChat(studyId)
            getAllChats()
            chatList.observe(viewLifecycleOwner, {
                chatAdapter.submitList(it)
            })
            userNickname.observe(viewLifecycleOwner, {
                nickname = it
            })
        }
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

                on(Socket.EVENT_CONNECT) { response ->
                    Logger.d("EVENT_CONNECT ${response.map { it.toString() }}")
                }

                on(Socket.EVENT_DISCONNECT) { response ->
                    Logger.d("EVENT_DISCONNECT ${response.map { it.toString() }}")
                }

                on(Socket.EVENT_MESSAGE) { response ->
                    Logger.d("EVENT_MESSAGE ${response.map { it.toString() }}")
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
                    putInt(ChattingViewModel.KEY_STUDY_ID, studyId)
                }
            }
    }
}