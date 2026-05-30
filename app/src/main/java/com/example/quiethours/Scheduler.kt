package com.example.quiethours

import android.content.Context
import androidx.work.*
import java.util.Calendar
import java.util.concurrent.TimeUnit

object Scheduler {

    private const val ENABLE_WORK = "quiet_enable"
    private const val DISABLE_WORK = "quiet_disable"

    fun scheduleQuietHours(
        context: Context,
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int
    ) {

        val now = Calendar.getInstance()

        val enableTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startHour)
            set(Calendar.MINUTE, startMinute)
            set(Calendar.SECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }

        val disableTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, endHour)
            set(Calendar.MINUTE, endMinute)
            set(Calendar.SECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }

        val enableDelay = enableTime.timeInMillis - now.timeInMillis
        val disableDelay = disableTime.timeInMillis - now.timeInMillis

        val enableRequest = OneTimeWorkRequestBuilder<EnableQuietHoursWorker>()
            .setInitialDelay(enableDelay, TimeUnit.MILLISECONDS)
            .addTag(ENABLE_WORK)
            .build()

        val disableRequest = OneTimeWorkRequestBuilder<DisableQuietHoursWorker>()
            .setInitialDelay(disableDelay, TimeUnit.MILLISECONDS)
            .addTag(DISABLE_WORK)
            .build()

        // IMPORTANT: prevent stacking duplicates
        WorkManager.getInstance(context).enqueueUniqueWork(
            ENABLE_WORK,
            ExistingWorkPolicy.REPLACE,
            enableRequest
        )

        WorkManager.getInstance(context).enqueueUniqueWork(
            DISABLE_WORK,
            ExistingWorkPolicy.REPLACE,
            disableRequest
        )
    }

    fun cancelQuietHours(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(ENABLE_WORK)
        WorkManager.getInstance(context).cancelUniqueWork(DISABLE_WORK)
    }
}