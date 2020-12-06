package com.iron.espresso.presentation.home.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.switchmaterial.SwitchMaterial
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.presentation.home.setting.adapter.SettingAdapter
import com.iron.espresso.presentation.home.setting.model.*
import com.iron.espresso.presentation.profile.ProfileActivity
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URI

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private lateinit var settingAdapter: SettingAdapter
    private var keyboardHeight = 0
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

        connect("roomId")


        binding.sendBtn.setOnClickListener {

            val message = binding.inputMessage.text
            if (message.isNotEmpty()) {
                chatSocket?.emit(CHAT, message)
            }
        }



        val settingList = arrayListOf<ItemType>()
        settingList.add(HeaderItem("", "", "", ""))

        resources.getStringArray(R.array.setting_category).forEachIndexed { count, category ->
            val itemList = arrayListOf<SettingItem>()
            when (count) {
                0 -> itemList.addAll(
                    resources.getStringArray(R.array.category_account).map {
                        SettingItem(it, SubItemType.IMAGE)
                    })
                1 -> itemList.addAll(
                    resources.getStringArray(R.array.category_notice).map {
                        SettingItem(it, SubItemType.SWITCH)
                    })
                2 -> itemList.addAll(
                    resources.getStringArray(R.array.category_info).map {
                        SettingItem(it, SubItemType.INFO)
                    })
                3 -> itemList.addAll(
                    resources.getStringArray(R.array.category_etc).map {
                        SettingItem(it, SubItemType.NONE)
                    })
            }
            settingList.add(SettingHeaderItem(category))
            settingList.addAll(itemList)
        }

        settingAdapter = SettingAdapter(settingList)
        settingAdapter.setItemClickListener(object : SettingAdapter.ItemClickListener {
            override fun onClick(view: View, noticeSwitch: SwitchMaterial?) {

                Toast.makeText(context, view.tag.toString(), Toast.LENGTH_SHORT).show()
                when (view.tag) {
                    getString(R.string.push_alarm) -> {
                        noticeSwitch!!.isChecked = !noticeSwitch.isChecked
                    }
                    getString(R.string.notice) -> {
                        val intent = Intent(context, SettingNoticeActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.help) -> {
                        val intent = Intent(context, SettingHelpActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.contact) -> {
                        val intent = Intent(context, SettingContactActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.terms) -> {
                        val intent = Intent(context, SettingTermsActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.policy) -> {
                        val intent = Intent(context, SettingPolicyActivity::class.java)
                        startActivity(intent)
                    }
                    0 -> {
                        val intent = Intent(context, SettingProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
        binding.settingRecyclerview.adapter = settingAdapter


        val testButton = Button(context).apply {
            text = "프로필 상세"
            setOnClickListener {
                startActivity(ProfileActivity.getInstance(context))
            }
        }

        (view as ViewGroup).addView(testButton)
    }

    private fun connect(roomId: String) {
        if (chatSocket?.connected() == true) return

        val options = IO.Options().apply {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            callFactory = client
            webSocketFactory = client
            transports = arrayOf("websocket")
            query = "token=${AuthHolder.accessToken}&study_id=1"
        }

        val chatManager = Manager(URI("http://3.35.154.27:3000"), options)

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

    override fun onDestroyView() {
        super.onDestroyView()

        chatSocket?.disconnect()
        chatSocket?.off(CHAT, onChatSendReceiver)
        chatSocket?.off(CHAT_MESSAGE, onChatReceiver)
    }

    companion object {
        private const val CHAT = "chat"
        private const val CHAT_MESSAGE = "message"

        fun newInstance() =
            SettingFragment()
    }
}