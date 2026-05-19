package com.example.quiethours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.quiethours.ui.theme.Scheduler

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            Scheduler.schedule(context)
        }
    }
}