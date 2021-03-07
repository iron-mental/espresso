package com.iron.espresso.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iron.espresso.Logger

class TerminalFirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Logger.d("${remoteMessage.notification?.title}")
        Logger.d("${remoteMessage.notification?.body}")
        Logger.d("${remoteMessage.notification?.channelId}")

//        Logger.d("${remoteMessage.data["Push Test"]}")
//        Logger.d("${remoteMessage.data.values.toList()}")


        if (remoteMessage.data.isNotEmpty()) {

//            MyNotificationManager().show(
//                applicationContext,
//                NOTIFICATION_ID++,
//                "data 노티인것인가?",
//                remoteMessage.data.values.toList().getOrNull(0) ?: ""
//            )
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            MyNotificationManager().show(applicationContext, NOTIFICATION_ID++, it)
        }
    }

    companion object {
        private var NOTIFICATION_ID = 0
    }
}