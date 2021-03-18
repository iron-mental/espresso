package com.iron.espresso.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.iron.espresso.R

class MyNotificationManager {

    fun show(
        context: Context,
        channelId: Int,
        remoteNotification: RemoteMessage.Notification
    ): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannelIfNeed(
            "일반 알림",
            NOTI_CHANNEL_ID,
            true,
            notificationManager
        )
        val notification: Notification = getNotification(context, remoteNotification.title.orEmpty(), remoteNotification.body.orEmpty())
        notificationManager.cancelAll()
        notificationManager.notify(channelId, notification)
        return true
    }

    fun show(
        context: Context,
        channelId: Int,
        title: String,
        message: String
    ): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannelIfNeed(
            "일반 알림",
            NOTI_CHANNEL_ID,
            true,
            notificationManager
        )
        val notification: Notification = getNotification(context, title, message)
        notificationManager.cancelAll()
        notificationManager.notify(channelId, notification)
        return true
    }


    private fun createNotificationChannelIfNeed(
        name: String?,
        channelId: String?,
        important: Boolean,
        notificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance =
                if (important) NotificationManager.IMPORTANCE_DEFAULT else NotificationManager.IMPORTANCE_LOW
            var channel = notificationManager.getNotificationChannel(channelId)
            if (channel == null) {
                channel = NotificationChannel(channelId, name, importance)
                // Configure the notification channel.
                channel.enableLights(true)
                // Sets the notification light color for notifications posted to this
                // channel, if the device supports this feature.
                channel.lightColor = Color.RED
                //        mChannel.enableVibration(true);
                //        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(channel)
            } else {
                if (channel.importance != importance) {
                    channel.importance = importance
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }

    private fun getNotification(
        context: Context,
        title: String,
        message: String,
    ): Notification {

        // normal
        val builder = NotificationCompat.Builder(
            context,
            NOTI_CHANNEL_ID
        ).apply {
            setAutoCancel(true)
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(R.drawable.icon_web)
//            setLargeIcon(largeIconBitmap)
//            setTicker(ticker) //상태 표시줄에 표시될 텍스트
//            setContentIntent(getToIntent(context, to, id))
//            setWhen(System.currentTimeMillis())
        }

        return builder.build()
    }

    companion object {
        private const val NOTI_CHANNEL_ID = "terminal_notification_01"
    }
}