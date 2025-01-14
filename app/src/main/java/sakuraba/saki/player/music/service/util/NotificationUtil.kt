package sakuraba.saki.player.music.service.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.app.NotificationCompat.MediaStyle
import lib.github1552980358.ktExtension.android.content.intent
import lib.github1552980358.ktExtension.android.graphics.toBitmap
import sakuraba.saki.player.music.BuildConfig
import sakuraba.saki.player.music.MainActivity
import sakuraba.saki.player.music.R
import sakuraba.saki.player.music.util.BitmapUtil.loadAlbumArtRaw
import sakuraba.saki.player.music.util.BitmapUtil.loadAudioArtRaw
import sakuraba.saki.player.music.util.Constants.FILTER_NOTIFICATION_NEXT
import sakuraba.saki.player.music.util.Constants.FILTER_NOTIFICATION_PAUSE
import sakuraba.saki.player.music.util.Constants.FILTER_NOTIFICATION_PLAY
import sakuraba.saki.player.music.util.Constants.FILTER_NOTIFICATION_PREV

private const val ChannelName = "PlayServiceNotification"

private const val ChannelId = "${BuildConfig.APPLICATION_ID}.PlayServiceNotification"

private const val NotificationId = 23333

val Context.createNotificationManager get(): NotificationManagerCompat = NotificationManagerCompat.from(this).createChannel

val NotificationManagerCompat.createChannel get(): NotificationManagerCompat = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(
            NotificationChannel(ChannelId, ChannelName, IMPORTANCE_HIGH).apply {
                // Remove vibration with SDK >= 26
                enableVibration(false)
                vibrationPattern = longArrayOf(0)
            }
        )
    }
}

fun Service.startForeground(notification: Notification) = startForeground(NotificationId, notification)

fun NotificationManagerCompat.update(notification: Notification) = notify(NotificationId, notification)

private val pendingIntentFlag get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE else 0

fun Context.getNotification(audioInfo: AudioInfo, isPaused: Boolean = false) =
    NotificationCompat.Builder(this, ChannelId).apply {
        setSmallIcon(R.mipmap.ic_launcher_round)
        setOngoing(false)
        priority = NotificationCompat.PRIORITY_MAX
        setStyle(MediaStyle().setMediaSession((this@getNotification as MediaBrowserServiceCompat).sessionToken))
        // Remove vibration under SDK 26
        setVibrate(longArrayOf(0))
        setContentTitle(audioInfo.audioTitle)
        setContentText("${audioInfo.audioArtist} - ${audioInfo.audioAlbum}")
        val bitmap = loadAudioArtRaw(audioInfo.audioId)
            ?: loadAlbumArtRaw(audioInfo.audioAlbumId)
            ?: ContextCompat.getDrawable(this@getNotification, R.drawable.ic_music)!!.toBitmap()
        setLargeIcon(bitmap)
        addAction(
            R.drawable.ic_prev,
            null,
            PendingIntent.getBroadcast(this@getNotification, 0, Intent(FILTER_NOTIFICATION_PREV), pendingIntentFlag)
        )
        
        if (isPaused) {
            addAction(
                R.drawable.ic_play,
                null,
                PendingIntent.getBroadcast(this@getNotification, 0, Intent(FILTER_NOTIFICATION_PLAY) , pendingIntentFlag)
            )
        } else {
            addAction(
                R.drawable.ic_pause,
                null,
                PendingIntent.getBroadcast(this@getNotification, 0, Intent(FILTER_NOTIFICATION_PAUSE), pendingIntentFlag)
            )
        }
    
        addAction(
            R.drawable.ic_next,
            null,
            PendingIntent.getBroadcast(this@getNotification, 0, Intent(FILTER_NOTIFICATION_NEXT), pendingIntentFlag)
        )

        setContentIntent(getActivity(this@getNotification, 0, intent(this@getNotification, MainActivity::class.java) {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }, pendingIntentFlag))

    }.build()