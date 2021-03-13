package com.iron.espresso.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iron.espresso.Logger
import com.iron.espresso.di.ApiModule
import io.reactivex.disposables.Disposable

class TerminalFirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        var disposable: Disposable? = null

        disposable = ApiModule.provideUserApi()
            .refreshPushToken()
            .subscribe({
                Logger.d("Refresh Success")
                disposable?.dispose()
            }, {
                Logger.d("$it")
                disposable?.dispose()
            })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Logger.d("${remoteMessage.notification?.title}")
        Logger.d("${remoteMessage.notification?.body}")
        Logger.d("${remoteMessage.notification?.channelId}")
        val data = remoteMessage.data.keys.map {
            it + " : " + remoteMessage.data[it]
        }
        Logger.d("$data")

        MyNotification(applicationContext, remoteMessage).show(
            applicationContext,
            NOTIFICATION_ID++
        )
    }

    companion object {
        private var NOTIFICATION_ID = 0
    }
}