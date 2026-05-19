package com.example.quiethours

import android.app.NotificationManager
import android.content.Context

class DndController(private val context: Context) {

    private val nm =
        context.getSystemService(NotificationManager::class.java)

    fun enableQuietHours() {
        if (!nm.isNotificationPolicyAccessGranted) return

        val policy = NotificationManager.Policy(
            NotificationManager.Policy.PRIORITY_CATEGORY_CALLS,
            0,
            0
        )

        nm.notificationPolicy = policy
        nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)
    }

    fun disableQuietHours() {
        if (!nm.isNotificationPolicyAccessGranted) return

        nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    }
}
