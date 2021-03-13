package com.iron.espresso.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.presentation.home.apply.ApplyStudyActivity


//터미널 title
//새로운 가입신청이 있습니다
//[pushEvent : apply_new, body : 새로운 가입신청이 있습니다, badge : 2, title : 터미널 title, alert_id : 2232, study_id : 670]

class MyNotification(context: Context, remoteMessage: RemoteMessage) {
    private val notificationBuilder: Notification =
        buildNotification(context, getChannelId(remoteMessage)) {
            setContentTitle(context, remoteMessage)
            setContentText(remoteMessage)
            setPriority(remoteMessage)
            setNumber(remoteMessage)
            setSmallIcon(R.drawable.ic_error_outline_black_48dp)
            setAutoCancel(true)

            setContentIntent(context, remoteMessage)
            addAction(context, remoteMessage)
        }

    private fun getChannelId(remoteMessage: RemoteMessage): String {
        return remoteMessage.notification?.channelId ?: remoteMessage.data["channel_id"]
        ?: NOTI_CHANNEL_ID
    }


    fun show(
        context: Context,
        channelId: Int
    ): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannelIfNeed(
            "일반 알림",
            NOTI_CHANNEL_ID,
            true,
            notificationManager
        )

        notificationManager.notify(channelId, notificationBuilder)
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

    private inline fun buildNotification(
        context: Context,
        channelId: String,
        action: NotificationCompat.Builder.() -> Unit
    ): Notification =
        NotificationCompat.Builder(context, channelId)
            .also(action)
            .build()

    private fun NotificationCompat.Builder.setContentTitle(
        context: Context,
        remoteMessage: RemoteMessage
    ) =
        setContentTitle(
            remoteMessage.notification?.title
                ?: remoteMessage.data["title"]
                ?: context.getString(R.string.app_name)
        )

    private fun NotificationCompat.Builder.setContentText(remoteMessage: RemoteMessage) =
        setContentText(
            remoteMessage.notification?.body
                ?: remoteMessage.data["body"]
        )

    private fun NotificationCompat.Builder.setPriority(remoteMessage: RemoteMessage) = setPriority(
        remoteMessage.notification?.notificationPriority
            ?: NotificationCompat.PRIORITY_DEFAULT
    )

    private fun NotificationCompat.Builder.setNumber(remoteMessage: RemoteMessage) =
        remoteMessage.data["badge"]?.toIntOrNull()?.let {
            setNumber(it)
        } ?: this

    private fun NotificationCompat.Builder.setContentIntent(
        context: Context,
        remoteMessage: RemoteMessage
    ) = setContentIntent(
        createToIntent(
            context,
            remoteMessage
        )
    )

    private fun NotificationCompat.Builder.addAction(
        context: Context,
        remoteMessage: RemoteMessage
    ): NotificationCompat.Builder {
        val action = remoteMessage.data["pushEvent"]
        if (!action.isNullOrEmpty()) {
            addAction(
                0,
                context.getString(R.string.check),
                createToIntent(context, remoteMessage)
            )
        }

        return this
    }

    private fun createToIntent(context: Context, remoteMessage: RemoteMessage): PendingIntent? {
        val action = ApplyStudyActivity.getIntent(context, remoteMessage.data["study_id"]?.toIntOrNull() ?: 0)
        try {
            return PendingIntent.getActivity(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT)
        } catch (e: NullPointerException) {
            Logger.d(e.toString())
        }
        return null
    }

    companion object {
        private const val NOTI_CHANNEL_ID = "terminal_notification_01"
    }
}