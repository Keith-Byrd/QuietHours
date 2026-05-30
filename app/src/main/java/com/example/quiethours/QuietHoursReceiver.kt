package com.example.quiethours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class QuietHoursReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val controller = DndController(context)

        when (intent.action) {

            "QUIET_HOURS_ON" -> {
                controller.enableQuietHours()
            }

            "QUIET_HOURS_OFF" -> {
                controller.disableQuietHours()
            }
        }
    }
}