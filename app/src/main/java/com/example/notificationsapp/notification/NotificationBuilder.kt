package com.example.notificationsapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.notificationsapp.R
import com.example.notificationsapp.ui.main.MainActivity


class NotificationBuilder {
    private lateinit var builder: NotificationCompat.Builder

    private val channelId = "com.example.notifications"

    fun showNotification(context: Context, count: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = buildPendingIntent(context, count)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                count.toString(),
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(context, channelId)
        } else {
            builder = NotificationCompat.Builder(context)
        }

        addStyles(count)

        builder.setContentIntent(pendingIntent)
        notificationManager.notify(count, builder.build())
    }

    private fun addStyles(count: Int){
        val description = "Notification $count"

        builder.setVibrate(longArrayOf())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setStyle(NotificationCompat.MessagingStyle(description)
                .addMessage(description, 0, "Chat heads active")
                .setConversationTitle("1d"))
        }

        builder.setSmallIcon(R.drawable.ic_baseline_message_24)
        builder.setDefaults(Notification.DEFAULT_VIBRATE)
        builder.setPriority(Notification.PRIORITY_HIGH)
        builder.setContentTitle("Chat heads active")
        builder.setContentText(description)
    }

    private fun buildPendingIntent(context: Context, count: Int): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("notificationNum", count)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, count, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, count, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
    }
}