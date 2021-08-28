package sakuraba.saki.player.music.service.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import sakuraba.saki.player.music.BuildConfig

private const val ChannelName = "PlayServiceNotification"

private const val ChannelId = "${BuildConfig.APPLICATION_ID}.PlayServiceNotification"

private const val NotificationId = 23333

val Context.createNotificationManager get(): NotificationManagerCompat = NotificationManagerCompat.from(this).createChannel

val NotificationManagerCompat.createChannel get(): NotificationManagerCompat = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(NotificationChannel(ChannelId, ChannelName, IMPORTANCE_HIGH))
    }
}

fun Service.startForeground(notification: Notification) = startForeground(NotificationId, notification)