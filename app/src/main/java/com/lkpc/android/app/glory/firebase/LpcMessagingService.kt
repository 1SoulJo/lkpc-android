package com.lkpc.android.app.glory.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.Notification
import java.util.*


class LpcMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "LpcMessagingService"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationId: Int = Random().nextInt(60000)

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "test_channel")
            .setContentTitle(remoteMessage.data["title"])
            .setSmallIcon(R.drawable.lpc_logo_symbol)
            .setContentText(remoteMessage.data["message"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        notificationManager.notify(
            notificationId,
            notificationBuilder.build()
        )

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName: CharSequence = getString(R.string.notification_channel_name)
        val adminChannelDescription = getString(R.string.notification_channel_description)
        val adminChannel = NotificationChannel(
            "test_channel",
            "for channel test",
            NotificationManager.IMPORTANCE_LOW
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }
}