package com.iron.espresso.model.source.remote

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.model.api.StudyApi
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.chat.SocketChatResponse
import com.iron.espresso.model.response.study.ChattingResponse
import io.reactivex.Single
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URI
import javax.inject.Inject


class ChatRemoteDataSourceImpl @Inject constructor(private val studyApi: StudyApi) :
    ChatRemoteDataSource {

    private var chatSocket: Socket? = null

    private val onChatReceiver = Emitter.Listener { args ->
        val message = args.getOrNull(0).toString()

        val response: SocketChatResponse = Gson().fromJson(message, SocketChatResponse::class.java)
        Logger.d("chatResponse: $response")
        callback?.invoke(response)
    }

    private var callback: ((SocketChatResponse) -> Unit)? = null

    override fun setChatCallback(callback: (SocketChatResponse) -> Unit) {
        this.callback = callback
    }

    override fun getChat(studyId: Int, date: Long, first: Boolean): Single<BaseResponse<ChattingResponse>> {
        return studyApi.getChat(studyId = studyId, date = date, first = first)
    }

    override fun sendMessage(chatMessage: String, uuid: String) {
        val data = JsonObject()
        data.addProperty("message", chatMessage)
        data.addProperty("uuid", uuid)

        chatSocket?.emit(CHAT, data)
    }

    override fun onDisconnect() {
        chatSocket?.disconnect()
        chatSocket?.off(CHAT_MESSAGE, onChatReceiver)
        chatSocket = null
    }

    override fun onConnect(studyId: Int) {
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
    }
}

interface ChatRemoteDataSource {
    fun getChat(studyId: Int, date: Long, first: Boolean): Single<BaseResponse<ChattingResponse>>

    fun onConnect(studyId: Int)

    fun onDisconnect()

    fun sendMessage(chatMessage: String, uuid: String)

    fun setChatCallback(callback: (SocketChatResponse) -> Unit)
}
