package com.example.quiethours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {

            val prefs = context.getSharedPreferences("quiethours", Context.MODE_PRIVATE)

            val enabled = prefs.getBoolean("enabled", false)

            if (!enabled) return

            val startHour = prefs.getInt("startHour", 22)
            val startMinute = prefs.getInt("startMinute", 0)
            val endHour = prefs.getInt("endHour", 7)
            val endMinute = prefs.getInt("endMinute", 0)

            Scheduler.scheduleQuietHours(
                context,
                startHour,
                startMinute,
                endHour,
                endMinute
            )
        }
    }
}